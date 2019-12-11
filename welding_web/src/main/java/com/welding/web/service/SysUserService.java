package com.welding.web.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.constants.Constants;
import com.welding.dao.SysRoleDao;
import com.welding.dao.SysUserDao;
import com.welding.dao.SysUserRoleDao;
import com.welding.dao.pojo.UserListVo;
import com.welding.dao.pojo.WelderListVo;
import com.welding.impl.SysUserDaoImpl;
import com.welding.model.BaseModel;
import com.welding.model.SysRole;
import com.welding.model.SysUser;
import com.welding.model.SysUserRole;
import com.welding.util.MData;
import com.welding.util.PageData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.pojo.AddRoleDto;
import com.welding.web.pojo.AddUserDto;
import com.welding.web.pojo.DeleteUserDto;
import com.welding.web.pojo.UpdateUserDto;
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

    public List<SysRole> queryRoleList() {
        return sysRoleDao.selectList(new QueryWrapper<>());
    }

    public SysRole queryRoleById(Integer roleId) {
        return sysRoleDao.selectById(roleId);
    }

    public IPage<WelderListVo> queryWelderListPage(IPage<WelderListVo> page, QueryWrapper<SysUser> wrapper) {
        return sysUserDao.queryWelderPage(page, wrapper);
    }

    public List<Integer> queryWelderUserIdList() {
        SysRole role = queryWeldingRole();

        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", role.getId());
        List<SysUserRole> userRoleList = sysUserRoleDao.selectList(wrapper);
        return userRoleList.stream().map(SysUserRole::getUserId).collect(Collectors.toList());
    }


    public SysUser queryUserByAccountNo(String accountNo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("account_no", accountNo);
        return sysUserDao.selectOne(wrapper);
    }

    public SysUser queryUserByWeldingNo(String weldingNo) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("welding_no", weldingNo);
        return sysUserDao.selectOne(wrapper);
    }

    /**
     * 查焊工角色
     *
     * @return
     */
    public SysRole queryWeldingRole() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_key", Constants.ROLE_WELDER);
        return sysRoleDao.selectOne(wrapper);
    }


    public SysUser queryUserById(Integer id) {
        return sysuserDaoImpl.queryUserById(id);
    }

    @Transactional
    public Integer addUser() {
        SysUser user = new SysUser();

        user.setAccountNo(RandomUtil.randomString(5));
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
        user.setAccountNo(RandomUtil.randomString(5));
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

            userListVo.setAccountNo(record.getAccountNo());
            userListVo.setUserName(record.getUserName());
            userListVo.setPassword(record.getPassword());
            userListVo.setRoleName("");
            userListVo.setGroupName("");
            userListVo.setTelephone(record.getTelephone());
            userListVo.setOfficePhone(record.getOfficePhone());
            userListVo.setEmail(record.getEmail());

            dataList.add(userListVo);
        }
//        IPage<UserListVo> ddd = sysUserDao.queryUserListPage(new PageData<UserListVo>(pageNo, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();

        result.put("pageTotal", userPage.getPages());
        result.put("pageNo", userPage.getCurrent());
        result.put("pageData", dataList);

        return result;
    }


    /**
     * 添加系统角色
     *
     * @param addRoleDto
     * @return
     */
    public MData addRole(AddRoleDto addRoleDto) {
        MData result = new MData();
        String roleKey = addRoleDto.getRoleKey();
        String roleName = addRoleDto.getRoleName();

        List<SysRole> roleList = queryRoleList();
        for (SysRole sysRole : roleList) {
            if (sysRole.getRoleKey().equals(roleKey)) {
                return result.error("角色编码已存在");
            }
            if (sysRole.getRoleName().equals(roleName)) {
                return result.error("角色名称已存在");
            }
        }
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey(roleKey);
        sysRole.setRoleName(roleName);
        sysRoleDao.insert(sysRole);

        result.setData(sysRole);
        return result;
    }

    /**
     * 添加用户
     *
     * @param addUserDto
     */
    @Transactional
    public MData addSysUser(AddUserDto addUserDto) {
        MData result = new MData();

        SysUser sysUser = queryUserByAccountNo(addUserDto.getAccountNo());
        if (sysUser != null) {
            return result.error("用户账号已存在");
        }
        //检查角色id
        SysRole role = queryRoleById(addUserDto.getRoleId());
        if (role == null) {
            return result.error("角色ID 参数错误");
        }

        SysUser user = new SysUser();
        user.setUserName(addUserDto.getUserName());
        user.setAccountNo(addUserDto.getAccountNo());
        String salt = ShiroUtils.randomSalt();
        user.setSalt(salt);
        user.setPassword(ShiroUtils.encryptPassword(addUserDto.getPassword(), salt));
        user.setTelephone(addUserDto.getTelephone());
        user.setOfficePhone(addUserDto.getOfficePhone());
        user.setEmail(addUserDto.getEmail());
        user.setGroupId(addUserDto.getGroupId());

        //添加用户
        sysUserDao.insert(user);
        //添加角色
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(addUserDto.getRoleId());
        sysUserRoleDao.insert(userRole);
        //添加用户日志。。。

        result.setData(user);
        return result;
    }

    /**
     * 删除用户
     *
     * @param deleteUserDto
     * @return
     */
    public MData deleteSysUser(DeleteUserDto deleteUserDto) {
        MData result = new MData();
        SysUser sysUser = queryUserByAccountNo(deleteUserDto.getAccountNo());
        if (sysUser == null) {
            return result.error("用户账号不存在");
        }
        SysUser user = new SysUser();
        user.setId(sysUser.getId());
        user.setStatus(Constants.User.DELETED);

        sysUserDao.updateById(user);
        result.setData(user);
        return result;
    }

    /**
     * 编辑系统用户
     *
     * @param addUserDto
     * @return
     */
    public MData updateSysUser(UpdateUserDto addUserDto) {
        MData result = new MData();
        SysUser sysUser = queryUserByAccountNo(addUserDto.getAccountNo());
        if (sysUser == null) {
            return result.error("用户账号不存在");
        }
        //检查角色id
        SysRole role = queryRoleById(addUserDto.getRoleId());
        if (role == null) {
            return result.error("角色ID 参数错误");
        }

        SysUser user = new SysUser();
        user.setId(sysUser.getId());
        user.setUserName(addUserDto.getUserName());
        user.setAccountNo(addUserDto.getAccountNo());
        user.setTelephone(addUserDto.getTelephone());
        user.setOfficePhone(addUserDto.getOfficePhone());
        user.setEmail(addUserDto.getEmail());
        user.setGroupId(addUserDto.getGroupId());

        //更新用户
        sysUserDao.updateById(user);

        result.setData(user);
        return result;
    }


}
