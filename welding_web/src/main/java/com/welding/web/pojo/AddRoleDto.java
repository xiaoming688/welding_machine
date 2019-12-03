package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author MM
 * @create 2019-12-02 17:01
 **/
@Data
public class AddRoleDto {
    @NotBlank(message = "roleName不能为空")
    private String roleName;
    @NotBlank(message = "roleKey不能为空")
    private String roleKey;
}
