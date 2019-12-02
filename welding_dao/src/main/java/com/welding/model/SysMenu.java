package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-12-02 15:22
 **/
@Data
@TableName("sys_menu")
public class SysMenu extends BaseModel implements Serializable {

    private String menuName;

    private String url;

    private String menuType;

    private Integer parentId;

    private Integer available;

    private String perms;

    private String icon;

    private Integer sortId;

}
