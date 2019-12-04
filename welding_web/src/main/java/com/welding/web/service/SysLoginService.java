package com.welding.web.service;

import com.welding.constants.Constants;
import com.welding.dao.SysUserDao;
import com.welding.dao.pojo.LoginUser;
import com.welding.model.AdminUser;
import com.welding.model.SysUser;
import com.welding.web.config.exception.user.UserBlockedException;
import com.welding.web.config.exception.user.UserNotExistsException;
import com.welding.web.config.exception.user.UserPasswordNotMatchException;
import com.welding.web.config.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author MM
 * @create 2019-12-03 15:18
 **/
@Slf4j
@Service
public class SysLoginService {

    private Map<String, AtomicInteger> loginRecordCache;

    //重试次数
    private String maxRetryCount = "5";

    @Autowired
    private SysUserService sysUserService;

    @PostConstruct
    public void init() {
        if (loginRecordCache == null) {
            loginRecordCache = new ConcurrentHashMap<>();
        }
    }


    /**
     * 登录业务判断
     *
     * @param username
     * @param password
     * @param loginType 登录方式，后续可能有验证码登录等。。
     * @return
     */
    public LoginUser login(String username, String password, String loginType) {
        SysUser user = sysUserService.queryUserByAccountNo(username);
        validate(user, password, loginType);
        LoginUser sysUser = new LoginUser(user);
        return sysUser;
    }


    public void validate(SysUser user, String password, String loginType) {
        //目前直接匹配查询
        if (user == null) {
            throw new UserNotExistsException();
        }
        if (user.getStatus().equals(Constants.User.BLOCKED)) {
            throw new UserBlockedException();
        }
        //验证码不需要校验密码

        String accountNo = user.getAccountNo();

        AtomicInteger retryCount = loginRecordCache.get(accountNo);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(accountNo, retryCount);
        }
        //
        if (!matches(user, password)) {
            loginRecordCache.put(accountNo, retryCount);
            throw new UserPasswordNotMatchException();
        }
        clearLoginRecordCache(accountNo);
    }

    public boolean matches(SysUser user, String password) {
        return user.getPassword().equals(ShiroUtils.encryptPassword(password, user.getSalt()));
    }


    public void clearLoginRecordCache(String accountNo) {
        loginRecordCache.remove(accountNo);
    }

}
