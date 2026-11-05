package com.domed.api.controller.transportController.vehicleWorkflow;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import com.domed.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/vehicleWorkflow")
public class VehicleWorkflowController {
    @Autowired private VehicleAccessPolicy access;
    @Autowired private IVehicleWorkflowService service;

    @PostMapping("/options.json")
    public Result<Map<String, Object>> options(@RequestHeader(value="Authorization", required=false) String token) {
        return Result.success(service.options(access.authenticate(token)));
    }

    @PostMapping("/drafts.json")
    public Result<Map<String, Object>> drafts(@RequestHeader(value="Authorization", required=false) String token,
                                               @RequestBody Map<String, Object> input) {
        return Result.success(service.listDrafts(access.authenticate(token), input));
    }
    @PostMapping("/getAcceptCarByLetterGid.json")
    public Result<Map<String, Object>> get(@RequestHeader(value="Authorization", required=false) String token,
                                            @RequestBody Map<String, Object> input) {
        return Result.success(service.getAcceptCarByLetterGid(access.authenticate(token), input));
    }
    @PostMapping("/holdRoadFirstIssueTemp.json")
    public Result<Map<String, Object>> save(@RequestHeader(value="Authorization", required=false) String token,
                                             @RequestBody Map<String, Object> input) {
        return Result.success(service.holdRoadFirstIssueTemp(access.authenticate(token), input));
    }
    @PostMapping("/submit.json")
    public Result<Map<String, Object>> submit(@RequestHeader(value="Authorization", required=false) String token,
                                               @RequestBody Map<String, Object> input) {
        return Result.success(service.submit(access.authenticate(token), input));
    }
    @PostMapping("/updateCarForecastAccept.json")
    public Result<Map<String, Object>> preAccept(@RequestHeader(value="Authorization", required=false) String token,
                                                  @RequestBody Map<String, Object> input) {
        return Result.success(service.updateCarForecastAccept(access.authenticate(token), input));
    }
    @PostMapping("/updateCarAccept.json")
    public Result<Map<String, Object>> accept(@RequestHeader(value="Authorization", required=false) String token,
                                               @RequestBody Map<String, Object> input) {
        return Result.success(service.updateCarAccept(access.authenticate(token), input));
    }
}
