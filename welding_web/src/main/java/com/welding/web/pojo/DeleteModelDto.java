package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author MM
 * @description
 * @create 2020-04-26 11:05
 **/
@Data
public class DeleteModelDto {
    @NotEmpty(message = "modelCode不能为空")
    private String modelCode;
}
