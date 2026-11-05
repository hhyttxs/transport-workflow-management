package com.domed.api.controller.clientController;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.domed.api.service.clientService.ISysOauthClientService;
import com.domed.common.result.Result;
import com.domed.domain.OAuth2Client;
import com.domed.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth-clients")
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class OauthClientController {

    private ISysOauthClientService iSysOauthClientService;

    @GetMapping("/getOAuth2ClientById")
    public Result<OAuth2Client> getOAuth2ClientById(@RequestParam String clientId) {
        return CommonUtil.controllerResult(() -> iSysOauthClientService.getByClientID(clientId));
    }
}
