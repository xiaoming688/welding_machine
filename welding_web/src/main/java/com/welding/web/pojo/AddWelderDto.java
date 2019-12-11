package com.welding.web.pojo;

import lombok.Data;

/**
 * @author MM
 * @create 2019-12-11 9:15
 **/
@Data
public class AddWelderDto {
    private String userName;
    private String accountNo;

    private String weldingNo;
    private String cardNo;
    private String stampNo;

    private Integer groupId;

    private String technicalLevel;
    private Integer sex;
    private String birthday;

}
