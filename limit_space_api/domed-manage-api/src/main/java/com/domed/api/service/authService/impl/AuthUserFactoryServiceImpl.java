package com.domed.api.service.authService.impl;

import com.domed.core.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthUserFactoryServiceImpl {

    @Autowired
    private AuthBackUserServiceImpl backUserService;

    @Autowired
    private AuthFrontUserServiceImpl frontUserService;
//
//    @Autowired
//    private AuthMobileUserServiceImpl mobileUserService;
//
//    @Autowired
//    private AuthOpenIdUserServiceImpl openIdUserService;
//
//    @Autowired
//    private AuthUnionIdUserServiceImpl unionIdUserService;
//
//    @Autowired
//    private AuthWarrantUserServiceImpl warrantUserService;

    public AuthUserServiceImpl getService(String type){
         switch (type){
            case "front":
                return frontUserService;
            case "back":
                return backUserService;
//            case "mobile":
//                return mobileUserService;
//            case "openId":
//                return openIdUserService;
//            case "unionId":
//                return unionIdUserService;
//            case "warrant":
//                return warrantUserService;
            default:
                throw new ApplicationException("登录类型错误");

        }
    }
}
