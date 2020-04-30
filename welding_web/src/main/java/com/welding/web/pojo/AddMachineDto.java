package com.welding.web.pojo;

import lombok.Data;

@Data
public class AddMachineDto {
    private String machineCode;
    private String name;
    private String description;
    private String brandName;
    private String modelName;
    private String machineType;
    private String collectionCode;
    private String nextMaintenance;
    private String address;
}
