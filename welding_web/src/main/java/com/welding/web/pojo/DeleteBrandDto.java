package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author MM
 * @description
 * @create 2020-04-26 10:10
 **/
@Data
public class DeleteBrandDto {
    @NotEmpty(message = "brandCode不能为空")
    private String brandCode;
}
