//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.security.extension.captcha;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.domed.core.exception.ApplicationException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class CaptchaTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "captcha";
    private final AuthenticationManager authenticationManager;
    private StringRedisTemplate redisTemplate;

    public CaptchaTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager, StringRedisTemplate redisTemplate) {
        super(tokenServices, clientDetailsService, requestFactory, "captcha");
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }

    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String validateCode = (String) parameters.get("code");
        String uuid = (String) parameters.get("uuid");
        Assert.isTrue(StrUtil.isNotBlank(validateCode), "验证码不能为空", new Object[0]);
        String validateCodeKey = "VALIDATE_CODE:" + uuid;
        String correctValidateCode = (String) this.redisTemplate.opsForValue().get(validateCodeKey);
        if (!validateCode.equals(correctValidateCode)) {
            throw new ApplicationException("验证码不正确");
        } else {
            this.redisTemplate.delete(validateCodeKey);
            String username = (String) parameters.get("username");
            String password = (String) parameters.get("password");
            parameters.remove("password");
            parameters.remove("code");
            parameters.remove("uuid");
            Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
            ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
            try {
                userAuth = this.authenticationManager.authenticate(userAuth);
            } catch (AccountStatusException var12) {
                throw new InvalidGrantException(var12.getMessage());
            } catch (BadCredentialsException var13) {
                throw new InvalidGrantException(var13.getMessage());
            }

            if (userAuth != null && userAuth.isAuthenticated()) {
                OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
                return new OAuth2Authentication(storedOAuth2Request, userAuth);
            } else {
                throw new InvalidGrantException("Could not authenticate user: " + username);
            }
        }
    }
}
