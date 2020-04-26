package com.welding.dao.pojo;

import lombok.Data;

@Data
public class MachineListVo {
    private String machineCode;
    private String machineModel;
    private String machineName;

    private String machineType;
    private String collectionCode;

    private String nextMaintenance;
    private String address;
    private String brandName;
    private String description;
}
