// 框架自带（实际应用的是 @/utils/request）
import request from '@/plugins/request';

export function getInvalidUrl () {
    return request({
        url: '/invalid-url',
        method: 'get'
    });
}
