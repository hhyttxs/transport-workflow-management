package com.domed.api.controller.acceptFormCommonController;

import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RefreshScope
@RestController
@CrossOrigin
@RequestMapping( value = "/accept" )
public class AcceptController {
}
