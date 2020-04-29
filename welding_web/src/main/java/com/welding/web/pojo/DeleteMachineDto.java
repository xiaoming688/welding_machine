package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeleteMachineDto {
    @NotEmpty(message = "machineCode不能为空")
    private String machineCode;
}
