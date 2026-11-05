package com.domed.api.controller.hy;

import com.domed.api.service.hy.IYhcdService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@CrossOrigin
@RequestMapping(value = "/yhcd")
public class YhcdController {

    @Autowired
    private IYhcdService yhcdService;


    @RequestMapping(value = "queryYhList.json", method = {RequestMethod.POST}, produces = "application/json"
    )
    public Result<Map> queryYhList(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> yhcdService.queryYhList(infoMap));
    }

    @RequestMapping(value = "saveCdgl.json", method = {RequestMethod.POST}, produces = "application/json"
    )
    public Result<Map> saveCdgl(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> yhcdService.saveCdgl(infoMap));
    }


}
