package com.domed.api.service.clientService.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.domed.api.service.clientService.ISysOauthClientService;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.exception.ApplicationException;
import com.domed.domain.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Title:
 * @Description: 
 * @author 高杨 
 * @date 2022/2/27 14:02
 * @return:  
 */
@Service
@RequiredArgsConstructor
public class SysOauthClientServiceImpl implements ISysOauthClientService {
    @Resource(name = "oauth2ClientDAO")
    private IGenericDAO<OAuth2Client> oauth2ClientDAO;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 清理客户端缓存
     */
    @Override
    public void cleanCache() {
        Set<String> keys = stringRedisTemplate.keys("auth:oauth-client:*");
        if (CollectionUtil.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }


    /**
     * @Title:
     * @Description: 获取客户端ID
     * @author 高杨 
     * @date 2022/2/27 14:35
     * @return:  
     */
    @Override
    public Result getByClientID(String clientId){
        OAuth2Client client = oauth2ClientDAO.get(clientId);
        if(client == null){
            throw new ApplicationException("客户端不存在");
        }
        return Result.success(client);
    }
}
