package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-12-04 10:23
 **/
@Data
@TableName(value = "welding_operate_log_detail")
public class WeldingOperateLogDetail extends BaseModel implements Serializable {

    private Integer logId;

    private String tableName;

    private String column;

    private String oldValue;

    private String newValue;

}
