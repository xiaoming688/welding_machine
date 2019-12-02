package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 */
@Data
public class AddUserDto {

    private String accountNo;

    private String userName;

    private String password;

    private Integer groupId;

    private Integer roleId;

    private String telephone;

    private String officePhone;

    private String email;

}
