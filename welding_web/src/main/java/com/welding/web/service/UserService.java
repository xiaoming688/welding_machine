package com.welding.web.service;

import cn.hutool.core.util.RandomUtil;
import com.welding.dao.UserDao;
import com.welding.model.AdminUser;
import com.welding.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据名字查找用户
     */
    public AdminUser selectUserByName(String name) {
        return userDao.findUserByName(name);
    }


    public List<Map<String, Object>> queryUserMap(){
        return userDao.findUserInfos();
    }



}
