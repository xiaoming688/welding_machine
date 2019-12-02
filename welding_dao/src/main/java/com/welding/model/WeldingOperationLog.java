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
@TableName(value = "welding_operation_log")
public class WeldingOperationLog extends BaseModel implements Serializable {

    private Integer userId;

    private String logType;

    private String content;

    private String operatorId;

    private Date operatorTime;

    private String businessType;

    private String operatorMenu;

}
