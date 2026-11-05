const pre = '/sytemResources/';

export default {
    path: '/sytemResources',
    title: '资源管理',
    header: 'home',
    icon: 'logo-buffer',
    children: [
        {
            path: `${pre}file_index`,
            title: '数据上传'
        },
        {
            path: `${pre}publicFile_index`,
            title: '公共文件',
        },
        {
            path: `${pre}goodsstorage_index`,
            title: '试剂药品',
        }
    ]
}
