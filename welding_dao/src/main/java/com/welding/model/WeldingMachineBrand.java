package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 焊机品牌
 * @author MM
 * @create 2019-12-02 16:22
 **/
@Data
@TableName(value = "welding_machine_brand")
public class WeldingMachineBrand extends BaseModel implements Serializable {
    private String name;
    private String description;
    private String status;
}
