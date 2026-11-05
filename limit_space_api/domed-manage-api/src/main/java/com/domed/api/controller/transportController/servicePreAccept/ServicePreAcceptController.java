package com.domed.api.controller.transportController.servicePreAccept;

import com.domed.api.service.transportService.servicePreAccept.IServicePreAcceptService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/servicePreAccept")
public class ServicePreAcceptController {
    @Autowired
    private IServicePreAcceptService service;

    @PostMapping(value = "queryAllPreAccept.json", produces = "application/json")
    public Result<Map> queryAllPreAccept(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> service.queryAllPreAccept(infoMap));
    }
}

