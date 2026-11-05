//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.domed.auth.security.core.clientdetails.ClientDetailsServiceImpl;
import com.domed.auth.security.core.userdetails.user.SysUserDetails;
import com.domed.auth.security.core.userdetails.user.SysUserDetailsServiceImpl;
import com.domed.auth.security.extension.captcha.CaptchaTokenGranter;
import com.domed.auth.security.extension.refresh.PreAuthenticatedUserDetailsService;
import com.domed.common.result.Result;
import com.domed.common.result.ResultCode;

import java.beans.ConstructorProperties;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final ClientDetailsServiceImpl clientDetailsService;
    private final SysUserDetailsServiceImpl sysUserDetailsService;
    private final StringRedisTemplate stringRedisTemplate;

    public void configure(ClientDetailsServiceConfigurer clients) {
        try {
            clients.withClientDetails(this.clientDetailsService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList();
        tokenEnhancers.add(this.tokenEnhancer());
        tokenEnhancers.add(this.jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        List<TokenGranter> granterList = new ArrayList(Arrays.asList(endpoints.getTokenGranter()));
        granterList.add(new CaptchaTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), this.authenticationManager, this.stringRedisTemplate));
        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
        endpoints.authenticationManager(this.authenticationManager).accessTokenConverter(this.jwtAccessTokenConverter()).tokenEnhancer(tokenEnhancerChain).tokenGranter(compositeTokenGranter).reuseRefreshTokens(true).tokenServices(this.tokenServices(endpoints));
    }

    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList();
        tokenEnhancers.add(this.tokenEnhancer());
        tokenEnhancers.add(this.jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(this.clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        Map<String, UserDetailsService> clientUserDetailsServiceMap = new HashMap();
        clientUserDetailsServiceMap.put("client", this.sysUserDetailsService);
        clientUserDetailsServiceMap.put("mall-admin-web", this.sysUserDetailsService);
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService(clientUserDetailsServiceMap));
        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair());
        return converter;
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), System.getenv("JWT_KEYSTORE_PASSWORD").toCharArray());
        KeyPair keyPair = factory.getKeyPair("jwt", System.getenv("JWT_KEY_PASSWORD").toCharArray());
        return keyPair;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = CollectionUtil.newHashMap();
            Object principal = authentication.getUserAuthentication().getPrincipal();
            if (principal instanceof SysUserDetails) {
                SysUserDetails sysUserDetails = (SysUserDetails) principal;
                additionalInfo.put("userId", sysUserDetails.getUserId());
                additionalInfo.put("username", sysUserDetails.getUsername());
                additionalInfo.put("userNameLable", sysUserDetails.getUserNameLable());
                if (StrUtil.isNotBlank(sysUserDetails.getAuthenticationMethod())) {
                    additionalInfo.put("authenticationMethod", sysUserDetails.getAuthenticationMethod());
                }
            }

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            Result result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }

    @ConstructorProperties({"authenticationManager", "clientDetailsService", "sysUserDetailsService", "stringRedisTemplate"})
    public AuthorizationServerConfig(final AuthenticationManager authenticationManager, final ClientDetailsServiceImpl clientDetailsService, final SysUserDetailsServiceImpl sysUserDetailsService, final StringRedisTemplate stringRedisTemplate) {
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.sysUserDetailsService = sysUserDetailsService;
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
