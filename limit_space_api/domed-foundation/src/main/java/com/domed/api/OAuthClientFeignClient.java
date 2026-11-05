package com.domed.api;

import com.domed.common.result.Result;
import com.domed.domain.OAuth2Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "limit-manage-api", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/oauth-clients/getOAuth2ClientById")
    Result<OAuth2Client> getOAuth2ClientById(@RequestParam String clientId);
}
