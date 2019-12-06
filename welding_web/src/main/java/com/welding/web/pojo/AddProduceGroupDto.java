package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @create 2019-12-02 17:06
 **/
@Data
public class AddProduceGroupDto {

    private String groupName;

    private String groupType;

    private Integer parentGroupId;

    private String address;

    private String email;

    private String leaderName;

    private String remark;

}
