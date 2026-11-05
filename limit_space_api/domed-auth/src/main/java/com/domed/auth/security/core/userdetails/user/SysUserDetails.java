//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.security.core.userdetails.user;

import com.domed.auth.common.enums.PasswordEncoderTypeEnum;
import com.domed.util.UserDTO;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SysUserDetails implements UserDetails {
    private String userId;
    private String authenticationMethod;
    private String username;
    private String userNameLable;
    private String password;
    private Boolean enabled;
    private Collection<SimpleGrantedAuthority> authorities;

    public SysUserDetails(UserDTO user) {
        this.setUserId(user.getGid());
        this.setUsername(user.getLoginJson());
        this.setUserNameLable(user.getName());
        this.setPassword(PasswordEncoderTypeEnum.MD5.getPrefix() + user.getPassword());
        this.setEnabled(true);
    }

    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getUserNameLable() {
        return this.userNameLable;
    }

    public void setUserNameLable(String userNameLable) {
        this.userNameLable = userNameLable;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setAuthenticationMethod(final String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(final Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SysUserDetails)) {
            return false;
        } else {
            SysUserDetails other = (SysUserDetails)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95: {
                    Object this$userId = this.getUserId();
                    Object other$userId = other.getUserId();
                    if (this$userId == null) {
                        if (other$userId == null) {
                            break label95;
                        }
                    } else if (this$userId.equals(other$userId)) {
                        break label95;
                    }

                    return false;
                }

                Object this$authenticationMethod = this.getAuthenticationMethod();
                Object other$authenticationMethod = other.getAuthenticationMethod();
                if (this$authenticationMethod == null) {
                    if (other$authenticationMethod != null) {
                        return false;
                    }
                } else if (!this$authenticationMethod.equals(other$authenticationMethod)) {
                    return false;
                }

                Object this$username = this.getUsername();
                Object other$username = other.getUsername();
                if (this$username == null) {
                    if (other$username != null) {
                        return false;
                    }
                } else if (!this$username.equals(other$username)) {
                    return false;
                }

                label74: {
                    Object this$userNameLable = this.getUserNameLable();
                    Object other$userNameLable = other.getUserNameLable();
                    if (this$userNameLable == null) {
                        if (other$userNameLable == null) {
                            break label74;
                        }
                    } else if (this$userNameLable.equals(other$userNameLable)) {
                        break label74;
                    }

                    return false;
                }

                label67: {
                    Object this$password = this.getPassword();
                    Object other$password = other.getPassword();
                    if (this$password == null) {
                        if (other$password == null) {
                            break label67;
                        }
                    } else if (this$password.equals(other$password)) {
                        break label67;
                    }

                    return false;
                }

                Object this$enabled = this.getEnabled();
                Object other$enabled = other.getEnabled();
                if (this$enabled == null) {
                    if (other$enabled != null) {
                        return false;
                    }
                } else if (!this$enabled.equals(other$enabled)) {
                    return false;
                }

                Object this$authorities = this.getAuthorities();
                Object other$authorities = other.getAuthorities();
                if (this$authorities == null) {
                    if (other$authorities != null) {
                        return false;
                    }
                } else if (!this$authorities.equals(other$authorities)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SysUserDetails;
    }

    public int hashCode() {
        int result = 1;
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $authenticationMethod = this.getAuthenticationMethod();
        result = result * 59 + ($authenticationMethod == null ? 43 : $authenticationMethod.hashCode());
        Object $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        Object $userNameLable = this.getUserNameLable();
        result = result * 59 + ($userNameLable == null ? 43 : $userNameLable.hashCode());
        Object $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        Object $enabled = this.getEnabled();
        result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
        Object $authorities = this.getAuthorities();
        result = result * 59 + ($authorities == null ? 43 : $authorities.hashCode());
        return result;
    }

    public String toString() {
        return "SysUserDetails(userId=" + this.getUserId() + ", authenticationMethod=" + this.getAuthenticationMethod() + ", username=" + this.getUsername() + ", userNameLable=" + this.getUserNameLable() + ", password=[REDACTED], enabled=" + this.getEnabled() + ", authorities=" + this.getAuthorities() + ")";
    }
}
