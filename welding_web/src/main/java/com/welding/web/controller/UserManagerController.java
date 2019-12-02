package com.welding.web.controller;


import com.welding.constants.Constants;
import com.welding.dao.pojo.LoginUser;
import com.welding.util.MData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.pojo.AddUserDto;
import com.welding.web.pojo.AddWorkerGroup;
import com.welding.web.pojo.GetUserListDto;
import com.welding.web.pojo.GetWorkerGroupListDto;
import com.welding.web.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "基础信息管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class UserManagerController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 获取生产组织分页信息
     *
     * @param getWorkerGroupListDto
     * @return
     */
    @ApiOperation(value = "获取生产组织信息", notes = "")
    @RequestMapping(value = "/getWorkerGroupList", method = RequestMethod.POST)
    public MData getWorkerGroupList(@RequestBody GetWorkerGroupListDto getWorkerGroupListDto) {

        MData result = new MData();
        Integer pageNo = getWorkerGroupListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getWorkerGroupListDto.getPageNo();
        Integer pageSize = getWorkerGroupListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getWorkerGroupListDto.getPageSize();

        String groupName = getWorkerGroupListDto.getGroupName();
        return result;
    }

    /**
     * 获取生产组织分页信息
     *
     * @param adWorkerGroup
     * @return
     */
    @ApiOperation(value = "添加生产组织", notes = "")
    @RequestMapping(value = "/addWorkerGroup", method = RequestMethod.POST)
    public MData addWorkerGroup(@RequestBody AddWorkerGroup adWorkerGroup) {
        MData result = new MData();
        return result;
    }


    /**
     * 删除生产组织
     *
     * @param adWorkerGroup
     * @return
     */
    @ApiOperation(value = "删除生产组织", notes = "")
    @RequestMapping(value = "/deleteWorkerGroup", method = RequestMethod.POST)
    public MData deleteWorkerGroup(@RequestBody AddWorkerGroup adWorkerGroup) {
        MData result = new MData();
        return result;
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
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public MData addUser(@RequestBody AddUserDto addUserDto) {
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
