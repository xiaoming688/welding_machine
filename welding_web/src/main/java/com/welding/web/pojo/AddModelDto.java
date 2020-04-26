package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author MM
 * @description
 * @create 2020-04-26 11:03
 **/
@Data
public class AddModelDto {
    @NotEmpty(message = "brandId不能为空")
    private String modelCode;
    private String name;
    private String description;
    @NotNull(message = "brandId不能为空")
    private Integer brandId;
}
