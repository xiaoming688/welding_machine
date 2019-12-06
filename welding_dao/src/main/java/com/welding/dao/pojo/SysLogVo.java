package com.welding.dao.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MM
 */
@Data
public class SysLogVo implements Serializable {
    private String logType;
    
    private Integer logId;

    private String operatorIp;

    private String accountNo;

    private String userName;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date operateTime;
}
