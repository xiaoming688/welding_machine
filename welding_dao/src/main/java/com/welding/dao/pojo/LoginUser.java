package com.welding.dao.pojo;

import com.welding.model.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginUser extends SysUser implements Serializable {

    private List<String> authority;

    public LoginUser() {
    }

    public LoginUser(SysUser user) {
        this.setUserName(user.getUserName());
        this.setAccountNo(user.getAccountNo());
        this.setTelephone(user.getTelephone());
        this.setCardNo(user.getCardNo());
    }
}
