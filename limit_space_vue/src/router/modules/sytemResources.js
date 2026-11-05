import BasicLayout from '@/layouts/basic-layout';

const meta = {
    auth: true
};

export default {
    path: '/sytemResources',
    name: 'sytemResources',
    meta,
    component: BasicLayout,
    children: [
        {
            path: 'file_index',
            name: `file_index`,
            meta: {
                ...meta,
                title: '数据上传',
            },
            component: () => import('@/pages/rongxun/sytemResources/file/file.vue')
        },
        {
            path: 'publicFile_index',
            name: 'publicFile_index',
            meta: {
                ...meta,
                title: '公共文件'
            },
            component: () => import('@/pages/rongxun/sytemResources/file/publicFile.vue')
        },
        {
            path: 'goodsstorage_index',
            name: 'goodsstorage_index',
            meta: {
                ...meta,
                title: '试剂药品'
            },
            component: () => import('@/pages/rongxun/sytemResources/goodsstorage/goodsstorage.vue')
        }
    ]
};
