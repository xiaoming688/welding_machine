package com.welding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MM
 * @create 2019-12-02 16:08
 **/
@Data
@TableName(value = "welding_class_group")
public class WeldingClassGroup extends BaseModel implements Serializable {

    private String groupName;

    private String leaderUserId;

    private String leaderUserName;

    private String address;

    private String remark;

    private String status;

}
