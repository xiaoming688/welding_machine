package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-11-29 11:44
 **/
@Data
@TableName("sys_user")
public class SysUser extends BaseModel implements Serializable {

    private String userNo;

    private Integer sex;
    private String employeeNo;

    private String userName;

    private String password;

    private String telephone;

    private String officePhone;

    private String email;

    private String groupId;

    private String technicalLevel;


}
