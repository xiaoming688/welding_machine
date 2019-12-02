package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MM
 * @create 2019-11-29 11:44
 **/
@Data
@TableName("sys_user")
public class SysUser extends BaseModel implements Serializable {

    private String accountNo;

    private String cardNo;

    private Integer sex;

    private String weldingNo;

    private String userName;

    private String password;

    private String telephone;

    private String officePhone;

    private String email;

    private String groupId;

    private String technicalLevel;

    private Date birthday;

    private String status;

}
