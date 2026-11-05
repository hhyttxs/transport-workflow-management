package com.domed.api.controller.authController;

import com.domed.api.service.authService.IAuthService;
import com.domed.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RefreshScope
@RestController
@CrossOrigin
@RequestMapping( value = "/auth" )
@Slf4j
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping( value = "getStaffFuntionModelList.json",  produces = "application/json" )
    public void browseApprovalList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,String> infoMap) {
        CommonUtil.controllerAction(log,request,response,()->authService.getStaffFuntionModelList(infoMap));
    }

    @PostMapping( value = "browseAuthList.json",  produces = "application/json" )
    public void browseAuthList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,String> infoMap) {
        CommonUtil.controllerAction(log,request,response,()->authService.browseAuthList(infoMap));
    }

    @PostMapping( value = "getStaffFuntionDepartmentList.json",  produces = "application/json" )
    public void getStaffFuntionDepartmentList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,String> infoMap) {
        CommonUtil.controllerAction(log,request,response,()->authService.getStaffFuntionDepartmentList(infoMap));
    }

}


