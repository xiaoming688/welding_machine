package com.welding.dao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLogVo implements Serializable {
    private String logType;
    
    private Integer logId;

    private String operatorIp;

    private String accountNo;

    private String userName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date operateTime;
}
