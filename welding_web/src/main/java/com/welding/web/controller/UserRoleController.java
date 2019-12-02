package com.welding.web.controller;

import com.welding.util.MData;
import com.welding.web.pojo.AddRoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MM
 * @create 2019-12-02 16:59
 **/
@Api(tags = "权限管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class UserRoleController {


    /**
     * 获取用角色列表
     *
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "")
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    public MData getRoleList() {
        MData result = new MData();
        return result;
    }

    /**
     * 添加角色
     *
     * @return
     */
    @ApiOperation(value = "添加角色", notes = "")
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public MData addRole(@RequestBody AddRoleDto addRoleDto) {
        MData result = new MData();
        return result;
    }

}
