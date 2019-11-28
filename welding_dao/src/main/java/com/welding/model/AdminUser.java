package com.welding.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MM
 * @create 2019-05-08 14:12
 **/
@Data
public class AdminUser implements Serializable {
    private static final long serialVersionUID = -3091099719828422495L;
    private Integer id;
    private String nickname;
    private String email;
    private String phone;
    private transient String password;
    private Date lastLoginTime;
    private Integer status;
    private Integer loginCount;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    private Date updateTime;
    private Integer merchantId;
    private String headImg;
    private String userType;

}
