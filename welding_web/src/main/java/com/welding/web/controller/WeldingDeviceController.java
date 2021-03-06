package com.welding.web.controller;

import com.welding.constants.Constants;
import com.welding.dao.pojo.MachineListVo;
import com.welding.dao.pojo.ModelListVo;
import com.welding.model.WeldingData;
import com.welding.util.MData;
import com.welding.util.PageData;
import com.welding.dao.pojo.BrandListVo;
import com.welding.web.pojo.*;
import com.welding.web.service.WeldingDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MM
 * @description
 * @create 2020-04-26 9:47
 **/
@Api(tags = "设备管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class WeldingDeviceController {

    @Autowired
    private WeldingDeviceService weldingDeviceService;


    @ApiOperation(value = "获取焊机同步首页数据", notes = "")
    @RequestMapping(value = "/getMachineSyncIndexData", method = RequestMethod.POST)
    public MData getMachineSyncIndexData(@RequestBody @Validated MachineIndexDto machineSyncDto) {
        MData result = new MData();
        Integer pageNo = machineSyncDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : machineSyncDto.getPageNo();
        Integer pageSize = machineSyncDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : machineSyncDto.getPageSize();

        machineSyncDto.setPageNo(pageNo);
        machineSyncDto.setPageSize(pageSize);

        PageData<WeldingData> pageData = weldingDeviceService.getMachineSyncIndexData(machineSyncDto);

        result.setData(pageData);

        return result;
    }

    @ApiOperation(value = "获取焊机同步数据", notes = "")
    @RequestMapping(value = "/getMachineSyncData", method = RequestMethod.POST)
    public MData getMachineSyncData(@RequestBody @Validated MachineSyncDto machineSyncDto) {
        MData result = new MData();
        Integer pageNo = machineSyncDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : machineSyncDto.getPageNo();
        Integer pageSize = machineSyncDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : machineSyncDto.getPageSize();

        machineSyncDto.setPageNo(pageNo);
        machineSyncDto.setPageSize(pageSize);

        PageData<WeldingData> pageData = weldingDeviceService.getMachineSyncData(machineSyncDto);

        result.setData(pageData);

        return result;
    }


    @ApiOperation(value = "获取品牌列表", notes = "")
    @RequestMapping(value = "/getBrandList", method = RequestMethod.POST)
    public MData getBrandList(@RequestBody GetBrandListDto getBrandListDto) {
        MData result = new MData();
        Integer pageNo = getBrandListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getBrandListDto.getPageNo();
        Integer pageSize = getBrandListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getBrandListDto.getPageSize();

        String brandCode = getBrandListDto.getBrandCode();
        PageData<BrandListVo> pageData = weldingDeviceService.getBrandList(pageNo, pageSize, brandCode);

        result.setData(pageData);
        return result;
    }

    @ApiOperation(value = "添加品牌", notes = "")
    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    public MData addBrand(@RequestBody @Validated AddBrandDto addBrandDto) {
        return weldingDeviceService.addBrand(addBrandDto);
    }

    @ApiOperation(value = "删除品牌", notes = "")
    @RequestMapping(value = "/deleteBrand", method = RequestMethod.POST)
    public MData deleteBrand(@RequestBody @Validated DeleteBrandDto deleteBrandDto) {
        return weldingDeviceService.deleteBrand(deleteBrandDto);
    }


    @ApiOperation(value = "设备型号", notes = "")
    @RequestMapping(value = "/getModelList", method = RequestMethod.POST)
    public MData getModelList(@RequestBody GetModelListDto getModelListDto) {
        MData result = new MData();
        Integer pageNo = getModelListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getModelListDto.getPageNo();
        Integer pageSize = getModelListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getModelListDto.getPageSize();

        String brandCode = getModelListDto.getModelCode();
        PageData<ModelListVo> pageData = weldingDeviceService.getModelList(pageNo, pageSize, brandCode);

        result.setData(pageData);
        return result;
    }


    @ApiOperation(value = "添加型号", notes = "")
    @RequestMapping(value = "/addModel", method = RequestMethod.POST)
    public MData addModel(@RequestBody @Validated AddModelDto addModelDto) {
        return weldingDeviceService.addModel(addModelDto);
    }

    @ApiOperation(value = "删除型号", notes = "")
    @RequestMapping(value = "/deleteModel", method = RequestMethod.POST)
    public MData deleteModel(@RequestBody @Validated DeleteModelDto deleteModelDto) {
        return weldingDeviceService.deleteModel(deleteModelDto);
    }


    @ApiOperation(value = "焊机信息", notes = "")
    @RequestMapping(value = "/getMachineList", method = RequestMethod.POST)
    public MData getModelList(@RequestBody GetMachineListDto getMachineListDto) {
        MData result = new MData();
        Integer pageNo = getMachineListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getMachineListDto.getPageNo();
        Integer pageSize = getMachineListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getMachineListDto.getPageSize();

        String brandCode = getMachineListDto.getMachineCode();
        String address = getMachineListDto.getAddress();
        PageData<MachineListVo> pageData = weldingDeviceService.getMachineList(pageNo, pageSize, brandCode, address);

        result.setData(pageData);
        return result;
    }

    @ApiOperation(value = "添加焊机", notes = "")
    @RequestMapping(value = "/addMachine", method = RequestMethod.POST)
    public MData addMachine(@RequestBody @Validated AddMachineDto addModelDto) {
        return weldingDeviceService.addMachine(addModelDto);
    }

    @ApiOperation(value = "删除焊机", notes = "")
    @RequestMapping(value = "/deleteMachine", method = RequestMethod.POST)
    public MData deleteMachine(@RequestBody @Validated DeleteMachineDto deleteMachineDto) {
        return weldingDeviceService.deleteMachine(deleteMachineDto);
    }


}
