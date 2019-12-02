package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-12-02 16:20
 **/
@Data
@TableName(value = "welding_machine_model")
public class WeldingMachineModel extends BaseModel implements Serializable {

    private String modelNo;

    private Integer brandId;

    private String name;

    private String description;

}
