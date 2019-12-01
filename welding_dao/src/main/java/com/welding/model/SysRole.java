package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role")
public class SysRole extends BaseModel implements Serializable {

    private String roleName;

    private String roleKey;

    private String remark;
}
