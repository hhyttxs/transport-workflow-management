package com.domed.api.service.clientService;

import com.domed.common.result.Result;
import com.domed.domain.OAuth2Client;

public interface ISysOauthClientService {
    void cleanCache();

    public Result getByClientID(String clientId);
}
