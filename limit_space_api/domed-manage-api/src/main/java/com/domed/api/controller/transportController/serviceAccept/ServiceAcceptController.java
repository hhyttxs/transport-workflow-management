package com.domed.api.controller.transportController.serviceAccept;

import com.domed.api.service.transportService.serviceAccept.IServiceAcceptService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/serviceAccept")
public class ServiceAcceptController {
    @Autowired
    private IServiceAcceptService service;

    @PostMapping(value = "queryAllAccept.json", produces = "application/json")
    public Result<Map> queryAllAccept(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> service.queryAllAccept(infoMap));
    }

    @PostMapping(value = "queryAllExamine.json", produces = "application/json")
    public Result<Map> queryAllExamine(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> service.queryAllExamine(infoMap));
    }
}

