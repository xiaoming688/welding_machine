package com.welding.impl;

import com.welding.dao.BaseDao;
import com.welding.dao.SysUserDao;
import com.welding.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author MM
 * @create 2019-11-29 12:03
 **/
@Service
public class SysUserDaoImpl {

    @Autowired
    private SysUserDao sysUserDao;

    public SysUser queryUserById(Integer id) {
        return sysUserDao.queryUserById(id);
    }


    public Integer add(SysUser o) {
        int id = sysUserDao.insert(o);
        return o.getId();
    }

    public List<Map<String, String>> queryUserMenu(Integer id) {
        return sysUserDao.queryUserPermission(id);
    }

    public void update(SysUser o) {
        sysUserDao.updateById(o);
    }
}
