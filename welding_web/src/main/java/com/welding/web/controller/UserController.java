package com.welding.web.controller;


import com.welding.model.AdminUser;
import com.welding.util.MData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.service.SysUserService;
import com.welding.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@ApiIgnore
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/query")
    public AdminUser testQuery() {
        return userService.selectUserByName("test");
    }

    @RequestMapping("/testLogin")
    public AdminUser testLogin() {
        return userService.selectUserByName("test");
    }

//    @RequestMapping("/userLogin")
//    public MData userLogin(
//            @RequestParam(name = "username") String userName,
//            @RequestParam(name = "password") String passWord) {
//        MData result = new MData();
//
//        String msg = messageSource.getMessage("no.view.permission", null, null);
//        try {
//            Subject subject = ShiroUtils.getSubject();
//            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
//            subject.login(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    @RequestMapping("/testMap")
    public MData testMap() {
        MData result = new MData();
//        SysUser data = sysUserService.queryUserById(1);
//
        List<Map<String, String>> data = sysUserService.queryUserMenu(1);
//        sysUserService.updateUser();
        result.put("data", data);
        return result;
    }

}
