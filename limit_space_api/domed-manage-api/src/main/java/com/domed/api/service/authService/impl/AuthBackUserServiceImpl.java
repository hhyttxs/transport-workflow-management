package com.domed.api.service.authService.impl;

import com.domed.core.exception.ApplicationException;
import com.domed.util.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class AuthBackUserServiceImpl extends AuthUserServiceImpl {

    @Override
    public UserDTO getUserInfoDetailInfo(String realUserName){
        UserDTO userDTO = this.getUserInfo(new HashMap()
        {{put("userName",realUserName);put("account",realUserName);}}); //mobile 相当于账号

        if(userDTO == null )  throw new ApplicationException("用户不存在");

//        if(!userDTO.getIsStaff())  throw new ApplicationException("用户名无后台权限");

        return userDTO;
    }
}
