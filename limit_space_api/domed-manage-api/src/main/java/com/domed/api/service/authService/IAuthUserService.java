package com.domed.api.service.authService;


import com.domed.common.result.Result;
import com.domed.util.UserDTO;

import java.util.Map;

public interface IAuthUserService {

    public Result<UserDTO> getUserInfo(String userName);

}
