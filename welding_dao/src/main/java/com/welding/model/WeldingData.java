package com.welding.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "welding_data")
public class WeldingData extends BaseModel implements Serializable {


    private String teamCode;

    private String process;

    private String equipCode;

    private String hjProcess;

    private String personCode;

    private String weldCode;

    private String voltage;

    private String position;

    private String layer;

    private String current;

    private String ssSpeed;

    private String hjSpeed;

    private String temp;

    private String humidity;

    private String cjTime;

    private String projectId;

    private String angle;

    private String warn;

    private String reformUnit;


    private String stationCode;

    private String hjType;

    private String uploadStatus;

    private Integer isHistory;

    private String requestData;

}
