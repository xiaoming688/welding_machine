package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author MM
 * @create 2019-12-03 10:10
 **/
@Data
public class LoginDto {

//    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码不正确")
    @NotBlank(message = "用户账户不能为空")
    private String accountNo;
    @NotBlank(message = "password不能为空")
    private String password;

//    private String captcha;
    private Boolean rememberMe;

}
