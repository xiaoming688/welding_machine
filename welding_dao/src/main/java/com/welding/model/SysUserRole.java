package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user_role")
public class SysUserRole extends BaseModel implements Serializable {

    private Integer roleId;

    private Integer userId;

}
