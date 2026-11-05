//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.service.authService.impl;

import cn.hutool.json.JSONObject;
import com.domed.auth.service.authService.IAuthService;
import com.domed.common.result.Result;
import com.domed.core.util.JwtUtils;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private RedisTemplate redisTemplate;

    public AuthServiceImpl() {
    }

    public Result logout() {
        JSONObject payload = JwtUtils.getJwtPayload();
        String jti = payload.getStr("jti");
        Long expireTime = payload.getLong("exp");
        if (expireTime != null) {
            long currentTime = System.currentTimeMillis() / 1000L;
            if (expireTime > currentTime) {
                this.redisTemplate.opsForValue().set("auth:token:blacklist:" + jti, (Object)null, expireTime - currentTime, TimeUnit.SECONDS);
            }
        } else {
            this.redisTemplate.opsForValue().set("auth:token:blacklist:" + jti, (Object)null);
        }

        return Result.success("注销成功");
    }
}
