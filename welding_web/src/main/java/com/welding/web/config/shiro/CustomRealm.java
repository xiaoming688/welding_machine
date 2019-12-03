package com.welding.web.config.shiro;

import com.welding.dao.pojo.LoginUser;
import com.welding.web.config.exception.user.UserBlockedException;
import com.welding.web.config.exception.user.UserNotExistsException;
import com.welding.web.config.exception.user.UserPasswordNotMatchException;
import com.welding.web.config.exception.user.UserPasswordRetryLimitExceedException;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.service.SysLoginService;
import com.welding.web.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author MM
 * @create 2019-11-28 15:54
 **/
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLoginService sysLoginService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LoginUser user = ShiroUtils.getSysUser();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //从数据库查权限
        //根据用户ID查询角色（role），放入到Authorization里。
        Set<String> roles = sysUserService.queryUserRoleKeys(user.getId());
        info.setRoles(roles);
        //根据用户ID查询权限（permission），放入到Authorization里。

        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        ShiroToken upToken = (ShiroToken) authenticationToken;
        String username = upToken.getUsername();
        String password = "";
        String loginType = upToken.getLoginType();
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        LoginUser user;
        try {
            user = sysLoginService.login(username, password, loginType);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过 " + e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
