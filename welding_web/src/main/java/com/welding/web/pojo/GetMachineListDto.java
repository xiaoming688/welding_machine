package com.welding.web.pojo;

import lombok.Data;

@Data
public class GetMachineListDto {
    private String machineCode;
    private String address;
    private Integer pageNo;
    private Integer pageSize;
}
