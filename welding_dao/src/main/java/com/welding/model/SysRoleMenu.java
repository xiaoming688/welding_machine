package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-12-02 15:35
 **/
@Data
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseModel implements Serializable {
    private Integer roleId;
    private Integer menuId;
}
