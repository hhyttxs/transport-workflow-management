package com.domed.api;

import com.domed.common.result.Result;
import com.domed.util.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(value = "limit-manage-api")
public interface UserFeignClient {

    @GetMapping("/users/username/{username}")
    Result<UserDTO> getUserByUserName(@PathVariable String username);
}
