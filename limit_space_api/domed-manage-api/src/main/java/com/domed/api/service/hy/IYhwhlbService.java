package com.domed.api.service.hy;

import com.domed.common.result.Result;

import java.util.Map;

public interface IYhwhlbService {


    Result queryUserList(Map infoMap);
    Result saveOrUpdateYhwhlb(Map infoMap);
    Result bindUserRole(Map infoMap);


}
