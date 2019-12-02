package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-12-02 16:17
 **/
@Data
@TableName(value = "welding_produce_group")
public class WeldingProduceGroup extends BaseModel implements Serializable {

    private String groupName;

    private Integer parentId;

    private Integer leaderUserId;

    private String groupType;

    private String telephone;

    private String email;

    private String address;

    private String status;

}
