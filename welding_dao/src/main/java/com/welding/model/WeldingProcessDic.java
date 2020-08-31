package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author MM
 * @description
 * @create 2020-08-31 14:28
 **/
@Data
@TableName(value = "welding_process_dic")
public class WeldingProcessDic extends BaseModel {
    private String processName;

    private String weldingMethod;

}
