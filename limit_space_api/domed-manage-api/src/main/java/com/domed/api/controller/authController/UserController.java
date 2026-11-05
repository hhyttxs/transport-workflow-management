package com.domed.api.controller.authController;

import com.domed.api.service.authService.IAuthUserService;
import com.domed.common.result.Result;
import com.domed.core.util.JwtUtils;
import com.domed.util.CommonUtil;
import com.domed.util.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    @Qualifier("AuthUserServiceImpl")
    private IAuthUserService authUserService;

    /**
     * @Title: 根据用户名获取用户信息
     * @Description: 
     * @author 高杨 
     * @date 2022/2/27 21:06
     * @return:  
     */
    @GetMapping("/username/{username}")
    public Result<UserDTO> getUserByUserName(@PathVariable String username) {
        return CommonUtil.controllerResult(() -> authUserService.getUserInfo(username));
    }

    /**
     * @Title: getCurrentUser
     * @Description: 获取当前登陆的用户信息
     * @author 高杨 
     * @date 2022/2/27 22:07
     * @return: com.domed.common.result.Result<UserVO> 
     */
    @GetMapping("/me")
    public Result<UserDTO> getCurrentUser() {
        // 用户基本信息
        String userName = JwtUtils.getUsername();
        Result<UserDTO> userDTO = authUserService.getUserInfo(userName);
        userDTO.getData().setPassword("");
        return CommonUtil.controllerResult(() -> userDTO);
    }

    @GetMapping("/checkUser.do")
    public void checkUser(){




    }

}
