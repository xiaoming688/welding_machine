package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @create 2019-12-03 14:24
 **/
@Data
public class GetSysLogListDto {

    private Integer pageNo;

    private Integer pageSize;

    private String logType;
}
