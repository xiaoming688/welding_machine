package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author MM
 */
@Data
public class AddUserDto {
    @NotBlank(message = "accountNo不能为空")
    private String accountNo;
    @NotBlank(message = "userName不能为空")
    private String userName;
    @NotBlank(message = "password不能为空")
    private String password;
    @NotNull(message = "groupId不能为空")
    private Integer groupId;
    @NotNull(message = "roleId不能为空")
    private Integer roleId;
    @NotBlank(message = "telephone不能为空")
    private String telephone;
    @NotBlank(message = "officePhone不能为空")
    private String officePhone;
    @NotBlank(message = "email不能为空")
    private String email;

}
