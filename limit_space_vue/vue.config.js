const Setting = require('./src/setting.env');
const localVehicle = require('./scripts/vehicle-local-test-config.cjs');
const isLocalVehicle = localVehicle.localMode(process.env, process.argv[2] === 'serve' ? 'serve' : 'build');

// 拼接路径
const resolve = dir => require('path').join(__dirname, dir);

// 增加环境变量
process.env.VUE_APP_VERSION = require('./package.json').version;
process.env.VUE_APP_BUILD_TIME = require('dayjs')().format('YYYY-M-D HH:mm:ss');

module.exports = {
    ...(isLocalVehicle ? {pages: {index: {entry: resolve('src/local-vehicle/main.js'), template: 'public/index.html'}}} : {}),
    publicPath: isLocalVehicle ? '/' : Setting.publicPath,
    lintOnSave: Setting.lintOnSave,
    outputDir: Setting.outputDir,
    assetsDir: Setting.assetsDir,
    runtimeCompiler: true,
    productionSourceMap: false,
    devServer: {
        publicPath: isLocalVehicle ? '/' : Setting.publicPath,
        overlay: {
            errors: true
        },
        ...(isLocalVehicle ? localVehicle.localServer(process.env.VEHICLE_LOCAL_TEST_KEY) : {})
    },
    css: {
        loaderOptions: {
            less: {

            }
        }
    },
    // 默认设置: https://github.com/vuejs/vue-cli/tree/dev/packages/@vue/cli-service/lib/config/base.js
    chainWebpack: config => {
        if (isLocalVehicle) {
            config.plugin('vehicle-local-test-transport')
                .use(require('webpack').NormalModuleReplacementPlugin, [/^@\/utils\/request$/, resolve('src/local-vehicle/request.js')]);
            config.plugin('vehicle-local-test-identity').use(require('webpack').NormalModuleReplacementPlugin, [/^@\/utils\/auth$/, resolve('src/local-vehicle/auth.js')]);
            config.plugin('full-system-local-test-transport').use(require('webpack').NormalModuleReplacementPlugin, [/^@\/plugins\/request$/, resolve('src/local-vehicle/request.js')]);
        }
        /**
         * 删除懒加载模块的 prefetch preload，降低带宽压力
         * https://cli.vuejs.org/zh/guide/html-and-static-assets.html#prefetch
         * https://cli.vuejs.org/zh/guide/html-and-static-assets.html#preload
         * 而且预渲染时生成的 prefetch 标签是 modern 版本的，低版本浏览器是不需要的
         */
        config.plugins
            .delete('prefetch')
            .delete('preload');
        // 解决 cli3 热更新失效 https://github.com/vuejs/vue-cli/issues/1559
        config.resolve
            .symlinks(true);
        config
            // 开发环境
            .when(process.env.NODE_ENV === 'development',
                  // sourcemap不包含列信息
                  config => config.devtool('cheap-source-map')
            )
            // 非开发环境
            .when(process.env.NODE_ENV !== 'development', config => {

            });
        // markdown
        config.module
            .rule('md')
            .test(/\.md$/)
            .use('text-loader')
            .loader('text-loader')
            .end();
        // image exclude
        const imagesRule = config.module.rule('images');
        imagesRule
            .test(/\.(png|jpe?g|gif|webp|svg)(\?.*)?$/)
            .exclude
            .add(resolve('src/assets/svg'))
            .end();
        // 重新设置 alias
        config.resolve.alias
            .set('@', resolve('src'))
            .set('@api', resolve('src/api'));
        // node
        config.node
            .set('__dirname', true)
            .set('__filename', true);
    }
};
