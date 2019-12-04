package com.welding.web.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteUserDto {
    @NotBlank(message = "accountNo不能为空")
    private String accountNo;
}
