package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @create 2019-12-11 9:36
 **/
@Data
public class GetWelderListDto {

    private String userName;
    private String weldingNo;

    private Integer pageNo;
    private Integer pageSize;

}
