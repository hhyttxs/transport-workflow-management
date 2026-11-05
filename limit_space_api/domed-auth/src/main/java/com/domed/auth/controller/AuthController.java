//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.domed.api.UserFeignClient;
import com.domed.auth.service.authService.IAuthService;
import com.domed.common.result.Result;
import com.domed.core.util.RequestUtils;
import com.domed.util.CommonUtil;
import com.domed.util.UserDTO;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.beans.ConstructorProperties;
import java.io.IOException;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/oauth"})
@CrossOrigin
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private TokenEndpoint tokenEndpoint;
    private RedisTemplate redisTemplate;
    private KeyPair keyPair;
    private IAuthService authService;
    private UserFeignClient userFeignClient;

    @PostMapping({"/token"})
    public Object postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        String userMap = parameters.get("username");
        String password = parameters.get("password");
        JSONObject jsonObject = JSONUtil.parseObj(userMap);
        String username = jsonObject.get("userName").toString();
        parameters.put("password",password);
        String clientId = RequestUtils.getOAuth2ClientId();
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(parameters));
        OAuth2AccessToken accessToken = (OAuth2AccessToken)this.tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return Result.success(accessToken);
    }

    @PostMapping({"/tokenTest"})
    public Result postAccessToken(@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        Map map = new HashMap();
        map.put("test", "gaoyang");
        return Result.success(map);
    }

    @PostMapping({"/logout"})
    public Result logout() {
        return CommonUtil.controllerResult(() -> {
            return this.authService.logout();
        });
    }

    @GetMapping({"/public-key"})
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey)this.keyPair.getPublic();
        RSAKey key = (new RSAKey.Builder(publicKey)).build();
        return (new JWKSet(key)).toJSONObject();
    }

    @GetMapping({"/loadUserByUserName"})
    public Result loadUserByUserName(String userName) {
        Result<UserDTO> userAuthDTO = this.userFeignClient.getUserByUserName(userName);
        return Result.success(userAuthDTO);
    }

    @ConstructorProperties({"tokenEndpoint", "redisTemplate", "keyPair", "authService", "userFeignClient"})
    public AuthController(final TokenEndpoint tokenEndpoint, final RedisTemplate redisTemplate, final KeyPair keyPair, final IAuthService authService, final UserFeignClient userFeignClient) {
        this.tokenEndpoint = tokenEndpoint;
        this.redisTemplate = redisTemplate;
        this.keyPair = keyPair;
        this.authService = authService;
        this.userFeignClient = userFeignClient;
    }
}
