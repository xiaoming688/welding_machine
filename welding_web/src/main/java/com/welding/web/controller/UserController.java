package com.welding.web.controller;


import com.welding.model.AdminUser;
import com.welding.util.MData;
import com.welding.web.config.ShiroUtils;
import com.welding.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/query")
    public AdminUser testQuery() {
        return userService.selectUserByName("test");
    }

    @RequestMapping("/testLogin")
    public AdminUser testLogin() {
        return userService.selectUserByName("test");
    }

    @RequestMapping("/userLogin")
    public MData userLogin (
            @RequestParam(name = "username") String userName,
            @RequestParam(name = "password") String passWord){
        MData result = new MData();
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
            subject.login(token);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
