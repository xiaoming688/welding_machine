package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @description
 * @create 2020-04-26 10:37
 **/
@Data
public class GetModelListDto {
    private String modelCode;
    private Integer pageNo;
    private Integer pageSize;
}
