package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @description
 * @create 2020-04-26 9:49
 **/
@Data
public class GetBrandListDto {
    private String brandCode;
    private Integer pageNo;
    private Integer pageSize;
}
