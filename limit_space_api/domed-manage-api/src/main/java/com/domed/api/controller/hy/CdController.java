package com.domed.api.controller.hy;

import com.domed.api.service.hy.ICdService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@CrossOrigin
@RequestMapping(value = "/cd")
public class CdController {

    @Autowired
    private ICdService cdService;

    @RequestMapping(value = "getTreeMenuList.json", method = {RequestMethod.POST}, produces = "application/json")
    public Result<Map> getTreeMenuList(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> cdService.getTreeMenuList(infoMap));
    }

}
