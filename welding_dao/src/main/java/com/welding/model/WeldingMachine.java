package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 焊机
 *
 * @author MM
 * @create 2019-12-02 16:22
 **/
@Data
@TableName(value = "welding_machine")
public class WeldingMachine extends BaseModel implements Serializable {
    private String weldingNo;
    private String description;
    private Integer brandId;
    private Integer modelId;
    private String machineType;
    private String collectionCode;
    private String nextMaintenance;
    private String address;
    private String status;
}
