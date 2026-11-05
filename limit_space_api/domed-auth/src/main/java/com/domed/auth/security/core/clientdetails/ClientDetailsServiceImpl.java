//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.security.core.clientdetails;

import com.domed.api.OAuthClientFeignClient;
import com.domed.auth.common.enums.PasswordEncoderTypeEnum;
import com.domed.common.result.Result;
import com.domed.domain.OAuth2Client;
import java.beans.ConstructorProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private final OAuthClientFeignClient oAuthClientFeignClient;

    @Cacheable(
            cacheNames = {"auth"},
            key = "'oauth-client:'+#clientId"
    )
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            Result<OAuth2Client> result = this.oAuthClientFeignClient.getOAuth2ClientById(clientId);
            if (Result.success().getCode().equals(result.getCode())) {
                OAuth2Client client = (OAuth2Client)result.getData();
                BaseClientDetails clientDetails = new BaseClientDetails(client.getClientId(), client.getResourceIds(), client.getScope(), client.getAuthorizedGrantTypes(), client.getAuthorities(), client.getWebServerRedirectUri());
                clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
                clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException var5) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }

    @ConstructorProperties({"oAuthClientFeignClient"})
    public ClientDetailsServiceImpl(final OAuthClientFeignClient oAuthClientFeignClient) {
        this.oAuthClientFeignClient = oAuthClientFeignClient;
    }
}
