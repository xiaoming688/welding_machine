package com.welding.web.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.dao.SysRoleDao;
import com.welding.dao.SysUserDao;
import com.welding.dao.SysUserRoleDao;
import com.welding.dao.pojo.UserListVo;
import com.welding.impl.SysUserDaoImpl;
import com.welding.model.BaseModel;
import com.welding.model.SysRole;
import com.welding.model.SysUser;
import com.welding.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MM
 * @create 2019-11-29 11:56
 **/
@Service
public class SysUserService {

    @Autowired
    private SysUserDaoImpl sysuserDaoImpl;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleDao sysRoleDao;


    public Set<String> queryUserRoleKeys(Integer userId) {
        List<Map<String, String>> userRoles = sysUserRoleDao.queryUserRoles(userId);
        Set<String> userRoleKeys = new HashSet<>();
        for (Map<String, String> userRole : userRoles) {
            userRoleKeys.add(userRole.get("roleKey"));
        }
        return userRoleKeys;
    }

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


    public Map<String, Object> queryUserList(Integer pageNo, Integer pageSize, String userNo) {

        IPage<SysUser> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userNo)) {
            wrapper.eq("u.user_no", userNo);
        }
        List<SysRole> roleList = sysRoleDao.selectList(new QueryWrapper<>());
        Map<Integer, SysRole> roleMap = roleList.stream().collect(Collectors.toMap(BaseModel::getId, t -> t));

        List<SysUserRole> userRoleList = sysUserRoleDao.selectList(new QueryWrapper<>());

        Map<Integer, List<SysUserRole>> userRoleMap =
                userRoleList.stream().collect(Collectors.groupingBy(SysUserRole::getUserId));

        IPage<SysUser> userPage = sysUserDao.selectPage(page, wrapper);
        List<UserListVo> dataList = new ArrayList<>();
        for (SysUser record : userPage.getRecords()) {
            UserListVo userListVo = new UserListVo();

            userListVo.setUserNo(record.getUserNo());
            userListVo.setUserName(record.getUserName());
            userListVo.setPassword(record.getPassword());
            userListVo.setRoleName("");
            userListVo.setGroupName("");
            userListVo.setTelephone(record.getTelephone());
            userListVo.setOfficePhone(record.getOfficePhone());
            userListVo.setEmail(record.getEmail());

            dataList.add(userListVo);
        }
//        IPage<UserListVo> ddd = sysUserDao.queryUserListPage(new Page<UserListVo>(pageNo, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();

        result.put("pageTotal", userPage.getPages());
        result.put("currentPage", userPage.getCurrent());
        result.put("pageRecords", dataList);

        return result;
    }

}
