package com.welding.web.controller;


import com.welding.constants.Constants;
import com.welding.dao.pojo.ClassGroupListVo;
import com.welding.dao.pojo.ProduceGroupListVo;
import com.welding.dao.pojo.WelderListVo;
import com.welding.util.MData;
import com.welding.util.PageData;
import com.welding.web.pojo.*;
import com.welding.web.service.WeldingGroupService;
import com.welding.web.service.WeldingWeldingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "基础信息管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class UserManageController {

    @Autowired
    private WeldingGroupService weldingGroupService;

    @Autowired
    private WeldingWeldingService weldingWeldingService;

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
        PageData<ProduceGroupListVo> pageData = weldingGroupService.getProduceGroupData(pageNo, pageSize, groupName);

        result.setData(pageData);

        return result;
    }

    /**
     * 获取生产上级组织
     *
     * @return
     */
    @ApiOperation(value = "获取生产上级组织", notes = "")
    @RequestMapping(value = "/getProduceParentGroup", method = RequestMethod.POST)
    public MData getProduceParentGroup() {
        MData result = new MData();
        result.setData(weldingGroupService.getProduceParentGroup());
        return result;
    }


    /**
     * 添加生产组织
     *
     * @param adWorkerGroup
     * @return
     */
    @ApiOperation(value = "添加生产组织", notes = "")
    @RequestMapping(value = "/addWorkerGroup", method = RequestMethod.POST)
    public MData addWorkerGroup(@RequestBody AddProduceGroupDto adWorkerGroup) {
        return weldingGroupService.addProduceGroup(adWorkerGroup);
    }


    /**
     * 删除生产组织
     *
     * @param deleteProduceGroup
     * @return
     */
    @ApiOperation(value = "删除生产组织", notes = "")
    @RequestMapping(value = "/deleteWorkerGroup", method = RequestMethod.POST)
    public MData deleteWorkerGroup(@RequestBody DeleteProduceGroupDto deleteProduceGroup) {
        return weldingGroupService.deleteProduceGroup(deleteProduceGroup);
    }


    /**
     * 查询班组列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询班组列表", notes = "")
    @RequestMapping(value = "/getClassGroupList", method = RequestMethod.POST)
    public MData getClassGroupList(@RequestBody GetWorkerGroupListDto getClassGroupListDto) {
        MData result = new MData();
        Integer pageNo = getClassGroupListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getClassGroupListDto.getPageNo();
        Integer pageSize = getClassGroupListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getClassGroupListDto.getPageSize();

        String groupName = getClassGroupListDto.getGroupName();

        PageData<ClassGroupListVo> pageData = weldingGroupService.getClassGroupData(pageNo, pageSize, groupName);
        result.setData(pageData);
        return result;
    }


    /**
     * 添加班组
     *
     * @param
     * @return
     */
    @ApiOperation(value = "添加班组", notes = "")
    @RequestMapping(value = "/addClassGroup", method = RequestMethod.POST)
    public MData addClassGroup(@RequestBody AddClassGroupDto addClassGroupDto) {
        return weldingGroupService.addClassGroup(addClassGroupDto);
    }


    /**
     * 刪除班组
     *
     * @param
     * @return
     */
    @ApiOperation(value = "刪除班组", notes = "")
    @RequestMapping(value = "/deleteClassGroup", method = RequestMethod.POST)
    public MData deleteClassGroup(@RequestBody DeleteProduceGroupDto deleteProduceGroup) {
        return weldingGroupService.deleteClassGroup(deleteProduceGroup);
    }


    /**
     * 查询焊工
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询焊工", notes = "")
    @RequestMapping(value = "/getWelderList", method = RequestMethod.POST)
    public MData getWelderList(@RequestBody @Validated GetWelderListDto getWelderListDto) {
        MData result = new MData();

        Integer pageNo = getWelderListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getWelderListDto.getPageNo();
        Integer pageSize = getWelderListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getWelderListDto.getPageSize();
        getWelderListDto.setPageNo(pageNo);
        getWelderListDto.setPageSize(pageSize);

        PageData<WelderListVo> dataPage = weldingWeldingService.getWelderList(getWelderListDto);
        result.setData(dataPage);
        return result;
    }

    /**
     * 添加焊工
     *
     * @param
     * @return
     */
    @ApiOperation(value = "添加焊工", notes = "")
    @RequestMapping(value = "/addWelder", method = RequestMethod.POST)
    public MData addWelder(@RequestBody @Validated AddWelderDto addWelderDto) {
        return weldingWeldingService.addWelder(addWelderDto);
    }





}
