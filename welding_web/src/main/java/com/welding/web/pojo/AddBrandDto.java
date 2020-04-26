package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author MM
 * @description
 * @create 2020-04-26 10:02
 **/
@Data
public class AddBrandDto {
    @NotEmpty(message = "brandCode 不能为空")
    private String brandCode;
    private String description;
    private String name;
}
