package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @create 2019-12-02 17:03
 **/
@Data
public class GetWorkerGroupListDto {
    private Integer pageNo;
    private Integer pageSize;
    private String groupName;
}
