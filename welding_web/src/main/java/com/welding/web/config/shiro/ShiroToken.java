package com.welding.web.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-06-17 11:42
 **/
@Data
public class ShiroToken extends UsernamePasswordToken implements Serializable {

    private String loginType;

    public ShiroToken(String username, String password, boolean rememberMe, String loginType) {
        super(username, password, rememberMe);
        this.loginType = loginType;
    }

}
