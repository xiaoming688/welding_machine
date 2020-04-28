package com.welding.web.controller;

import com.welding.constants.Constants;
import com.welding.dao.pojo.LoginUser;
import com.welding.model.SysRole;
import com.welding.util.MData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.pojo.*;
import com.welding.web.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MM
 * @create 2019-12-02 16:59
 **/
@Api(tags = "权限管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class UserRoleController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用角色列表
     *
     * @return
     */
    @ApiOperation(value = "获取角色信息列表", notes = "")
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    @RequiresRoles(value = {"superadmin", "admin"}, logical = Logical.OR)
    public MData getRoleList() {
        MData result = new MData();

        List<SysRole> roleList = sysUserService.queryRoleList();

        List<Map<String, String>> dataList = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("roleKey", sysRole.getRoleKey());
            dataMap.put("roleName", sysRole.getRoleName());
            dataMap.put("roleId", String.valueOf(sysRole.getId()));
            dataList.add(dataMap);
        }
        result.setData(dataList);

        return result;
    }

    /**
     * 添加角色
     *
     * @return
     */
    @ApiOperation(value = "添加角色", notes = "")
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @RequiresRoles(value = {"superadmin", "admin"}, logical = Logical.OR)
    public MData addRole(@RequestBody @Validated AddRoleDto addRoleDto) {
        return sysUserService.addRole(addRoleDto);
    }


    /**
     * 获取用户分页信息
     *
     * @param getUserListDto
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public MData getUserList(@RequestBody GetUserListDto getUserListDto) {

        MData result = new MData();
        Integer pageNo = getUserListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getUserListDto.getPageNo();
        Integer pageSize = getUserListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getUserListDto.getPageSize();

        String userNo = getUserListDto.getUserNo();
        Map<String, Object> dataList = sysUserService.queryUserList(pageNo, pageSize, userNo);
        result.put("data", dataList);
        return result;
    }


    /**
     * 添加用户
     *
     * @param addUserDto
     * @return
     */
    @ApiOperation(value = "添加用户", notes = "")
//    @RequiresRoles(value = {"superadmin"})
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public MData addUser(@RequestBody @Validated AddUserDto addUserDto) {
        LoginUser user = ShiroUtils.getSysUser();
        return sysUserService.addSysUser(addUserDto);
    }


    /**
     * 修改用户信息
     *
     * @return
     */
    @ApiOperation(value = "修改用户信息", notes = "")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public MData updateUserInfo(@RequestBody UpdateUserDto addUserDto) {
        return sysUserService.updateSysUser(addUserDto);
    }

    /**
     * 删除用户
     *
     * @param deleteUserDto
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public MData deleteUser(@RequestBody @Validated DeleteUserDto deleteUserDto) {
        return sysUserService.deleteSysUser(deleteUserDto);
    }

}
