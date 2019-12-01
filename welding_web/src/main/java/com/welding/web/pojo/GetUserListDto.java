package com.welding.web.pojo;

import lombok.Data;

/**
 * 查询用户列表参数
 */
@Data
public class GetUserListDto {
    private Integer pageNo;
    private Integer pageSize;
    private String userNo;
}
