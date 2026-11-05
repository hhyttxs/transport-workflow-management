// Optional development-only reuse of an existing dependency installation.
const path=require('path');
const base=require('../limit_space_vue/vue.config.js');
module.exports={...base,chainWebpack(config){
  if(base.chainWebpack)base.chainWebpack(config);
  const modules=process.env.TRANSPORT_FRONTEND_DEPENDENCIES;
  if(modules){config.resolve.modules.add(path.resolve(modules));config.resolveLoader.modules.add(path.resolve(modules));}
}};
