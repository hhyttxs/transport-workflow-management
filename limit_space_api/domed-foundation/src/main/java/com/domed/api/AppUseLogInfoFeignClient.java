package com.domed.api;

import com.domed.common.result.Result;
import com.domed.util.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@FeignClient(value = "limit-manage-api")
public interface AppUseLogInfoFeignClient {

    @PostMapping("/appkeyLog/addAppKeyLog.json")
    public Result<Map> addAppKeyLog(@RequestBody Map infoMap);

    @PostMapping("/appkeyLog/updateAppKeyLog.json")
    public Result<Map> updateAppKeyLog(@RequestBody Map infoMap);
}
