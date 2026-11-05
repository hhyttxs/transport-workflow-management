//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.security.extension.refresh;

import com.domed.common.enmus.AuthenticationMethodEnum;
import com.domed.core.util.RequestUtils;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {
    private Map<String, UserDetailsService> userDetailsServiceMap;

    public PreAuthenticatedUserDetailsService(Map<String, UserDetailsService> userDetailsServiceMap) {
        Assert.notNull(userDetailsServiceMap, "userDetailsService cannot be null.");
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService must be set");
    }

    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        String clientId = RequestUtils.getOAuth2ClientId();
        AuthenticationMethodEnum authenticationMethodEnum = AuthenticationMethodEnum.getByValue(RequestUtils.getAuthenticationMethod());
        UserDetailsService userDetailsService = (UserDetailsService)this.userDetailsServiceMap.get(clientId);
        if (!clientId.equals("mall-admin-web") && !clientId.equals("client")) {
            return userDetailsService.loadUserByUsername(authentication.getName());
        } else {
            switch (authenticationMethodEnum) {
                default:
                    return userDetailsService.loadUserByUsername(authentication.getName());
            }
        }
    }

    public PreAuthenticatedUserDetailsService() {
    }
}
