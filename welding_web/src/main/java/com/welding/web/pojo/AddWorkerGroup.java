package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @create 2019-12-02 17:06
 **/
@Data
public class AddWorkerGroup {

    private String groupName;

    private Integer groupType;

    private Integer parentGroupId;

    private String location;

    private String email;

    private String name;

    private String remark;

}
