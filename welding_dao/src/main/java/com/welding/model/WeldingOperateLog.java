package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MM
 * @create 2019-12-02 16:14
 **/
@Data
@TableName(value = "welding_operate_log")
public class WeldingOperateLog extends BaseModel implements Serializable {

    private Integer userId;

    private String logType;

    private String content;

    private Date operateTime;

    private String operateIp;

    private String operateRoute;

}
