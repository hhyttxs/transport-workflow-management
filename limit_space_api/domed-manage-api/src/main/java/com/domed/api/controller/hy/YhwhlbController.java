package com.domed.api.controller.hy;

import com.domed.api.service.hy.IClbService;
import com.domed.api.service.hy.IYhwhlbService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@CrossOrigin
@RequestMapping(value = "/yhwhlb")
public class YhwhlbController {

    @Autowired
    private IYhwhlbService yhwhlbService;


    @RequestMapping(value = "queryUserList.json", method = {RequestMethod.POST}, produces = "application/json"
    )
    public Result<Map> queryUserList(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> yhwhlbService.queryUserList(infoMap));
    }
    @RequestMapping(value = "saveOrUpdateUserInfo.json", method = {RequestMethod.POST}, produces = "application/json")
    public Result<Map> saveOrUpdateYhwhlb(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> yhwhlbService.saveOrUpdateYhwhlb(infoMap));
    }
    @RequestMapping(value = "bindUserRole.json", method = {RequestMethod.POST}, produces = "application/json")
    public Result<Map> bindUserRole(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> yhwhlbService.bindUserRole(infoMap));
    }
}
