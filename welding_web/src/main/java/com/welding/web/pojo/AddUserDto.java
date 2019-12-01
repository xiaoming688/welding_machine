package com.welding.web.pojo;

import lombok.Data;

@Data
public class AddUserDto {
    private String userNo;

    private String userName;

    private String password;

    private Integer groupId;

    private Integer roleId;

    private String telephone;

    private String officePhone;

    private String email;

}
