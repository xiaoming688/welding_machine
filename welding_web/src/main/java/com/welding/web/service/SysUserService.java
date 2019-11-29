package com.welding.web.service;

import cn.hutool.core.util.RandomUtil;
import com.welding.impl.SysUserDaoImpl;
import com.welding.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author MM
 * @create 2019-11-29 11:56
 **/
@Service
public class SysUserService {

    @Autowired
    private SysUserDaoImpl sysuserDaoImpl;

    public SysUser queryUserById(Integer id) {
        return sysuserDaoImpl.queryUserById(id);
    }

    @Transactional
    public Integer addUser() {
        SysUser user = new SysUser();

        user.setUserNo(RandomUtil.randomString(5));
        user.setUserName("sdfdsf");
        user.setPassword("aaaaa");
        user.setTelephone("14455555555");
        user.setOfficePhone("028-1313131");
//        user.setEmail("ttt@qq.com");
        return sysuserDaoImpl.add(user);
    }

    public void updateUser() {
        SysUser user = new SysUser();
        user.setId(2);
        user.setUserNo(RandomUtil.randomString(5));
        user.setUserName("test2....");
        sysuserDaoImpl.update(user);
    }

    public List<Map<String, String>> queryUserMenu(Integer id) {
        return sysuserDaoImpl.queryUserMenu(id);
    }

}
