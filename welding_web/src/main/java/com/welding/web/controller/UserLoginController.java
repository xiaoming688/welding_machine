package com.welding.web.controller;

import com.welding.constants.Constants;
import com.welding.dao.pojo.LoginUser;
import com.welding.util.MData;
import com.welding.web.config.shiro.ShiroToken;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.pojo.LoginDto;
import com.welding.web.service.SysOperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author MM
 * @create 2019-12-03 10:07
 **/
@Api(tags = "用户登录接口", description = "")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private SysOperateLogService sysOperateLogService;

    @ApiOperation(value = "用户登入", notes = "")
    @PostMapping("/login")
    public MData userLogin(@RequestBody @Validated LoginDto loginDto) {

        MData result = new MData();

        String username = loginDto.getAccountNo();
        String pwd = loginDto.getPassword();
        Boolean rememberMe = loginDto.getRememberMe();
        log.info("pwd: {}, username: {}", pwd, username);
        ShiroToken token = new ShiroToken(username, pwd, rememberMe, Constants.LOGINTYPE_ACCOUNT_PASSWORD);
        Subject subject = ShiroUtils.getSubject();
        try {
            subject.login(token);
            LoginUser user = ShiroUtils.getSysUser();
            result.put("nickName", user.getUserName());
            result.put("currentAuthority", user.getAuthority());

            sysOperateLogService.addLoginLog(Constants.LOG.LOGIN, user.getId());
            return result;
        } catch (AuthenticationException e) {
            String msg = StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : "用户或密码错误";
            return result.error(msg);
        }
    }


    /**
     * 退出
     *
     * @return 1
     */
    @ApiOperation(value = "用户退出", notes = "")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public MData logout() {
        MData result = new MData();
        try {
            SecurityUtils.getSubject().logout();
            result.setMessage("退出成功");
        } catch (Exception e) {
            result.error("退出失败");
            log.error("errorMessage:" + e.getMessage());
        }
        return result;
    }

    /**
     * currentUser
     *
     * @return 1
     */
    @ApiOperation(value = "当前用户", notes = "")
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public MData currentUser() {
        MData result = new MData();
        try {
            LoginUser user = ShiroUtils.getSysUser();
            if (user == null) {
                result.put("isLogin", false);
                return result.error("未登录");
            }
            result.put("name", user.getUserName());
            result.put("notifyCount", 0);
            result.put("avatar", "no");
            result.put("isLogin", true);
        } catch (Exception e) {
            result.error("失败");
            log.error("errorMessage:" + e.getMessage());
        }
        return result;
    }
}
