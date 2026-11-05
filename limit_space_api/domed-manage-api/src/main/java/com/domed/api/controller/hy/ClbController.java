package com.domed.api.controller.hy;

import com.domed.api.service.hy.IClbService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@CrossOrigin
@RequestMapping(value = "/clb")
public class ClbController {

    @Autowired
    private IClbService clbService;

    @RequestMapping(value = "saveOrUpdateClb.json", method = {RequestMethod.POST}, produces = "application/json")
    public Result<Map> saveOrUpdateClb(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> clbService.saveOrUpdateClb(infoMap));
    }

    @RequestMapping(value = "queryDraftList.json", method = {RequestMethod.POST}, produces = "application/json"
    )
    public Result<Map> queryDraftList(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> clbService.queryDraftList(infoMap));
    }

    @RequestMapping(value = "getClbInfoByGid.json", method = {RequestMethod.POST}, produces = "application/json"
    )
    public Result<Map> getClbInfoByGid(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> clbService.getClbInfoByGid(infoMap));
    }

}
