//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.security.core.userdetails.user;

import com.domed.api.UserFeignClient;
import com.domed.common.result.Result;
import com.domed.common.result.ResultCode;
import com.domed.util.UserDTO;
import java.beans.ConstructorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("sysUserDetailsService")
public class SysUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(SysUserDetailsServiceImpl.class);
    private final UserFeignClient userFeignClient;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDetails userDetails = null;
        Result<UserDTO> result = this.userFeignClient.getUserByUserName(username);
        if (Result.isSuccess(result)) {
            UserDTO user = (UserDTO)result.getData();
            if (null != user) {
                userDetails = new SysUserDetails(user);
            }
        }

        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        } else {
            return userDetails;
        }
    }

    @ConstructorProperties({"userFeignClient"})
    public SysUserDetailsServiceImpl(final UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }
}
