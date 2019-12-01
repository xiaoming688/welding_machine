package com.welding.web.controller;


import com.welding.constants.Constants;
import com.welding.dao.pojo.LoginUser;
import com.welding.util.MData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.pojo.AddUserDto;
import com.welding.web.pojo.GetUserListDto;
import com.welding.web.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "用户管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class UserManagerController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户分页信息
     *
     * @param getUserListDto
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public MData getUserList(@RequestBody GetUserListDto getUserListDto) {

        Integer pageNo = getUserListDto.getPageNo() == null ? Constants.DEFAULT_PAGE_NO : getUserListDto.getPageNo();
        Integer pageSize = getUserListDto.getPageSize() == null ? Constants.DEFAULT_PAGE_SIZE : getUserListDto.getPageSize();

        MData result = new MData();
        Map<String, Object> dataList = sysUserService.queryUserList(pageNo, pageSize, getUserListDto.getUserNo());
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
    @RequiresRoles(value = {"superadmin"})
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public MData addUser() {
        LoginUser user = ShiroUtils.getSysUser();
        MData result = new MData();
        return result;
    }


    /**
     * 修改用户信息
     *
     * @return
     */
    @ApiOperation(value = "修改用户信息", notes = "")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public MData updateUserInfo() {
        MData result = new MData();
        return result;
    }

    /**
     * 删除用户
     *
     * @param addUserDto
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public MData deleteUser(@RequestBody AddUserDto addUserDto) {
        MData result = new MData();
        return result;
    }

}
