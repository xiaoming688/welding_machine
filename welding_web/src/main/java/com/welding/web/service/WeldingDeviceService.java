package com.welding.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.constants.Constants;
import com.welding.dao.WeldingDataDao;
import com.welding.dao.WeldingMachineBrandDao;
import com.welding.dao.WeldingMachineDao;
import com.welding.dao.WeldingModelDao;
import com.welding.dao.pojo.MachineListVo;
import com.welding.dao.pojo.ModelListVo;
import com.welding.model.WeldingData;
import com.welding.model.WeldingMachine;
import com.welding.model.WeldingMachineBrand;
import com.welding.model.WeldingMachineModel;
import com.welding.util.MData;
import com.welding.util.PageData;
import com.welding.dao.pojo.BrandListVo;
import com.welding.web.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author MM
 * @description
 * @create 2020-04-26 9:52
 **/
@Service
public class WeldingDeviceService {

    @Autowired
    private WeldingMachineBrandDao weldingMachineBrandDao;

    @Autowired
    private WeldingModelDao weldingModelDao;

    @Autowired
    private WeldingMachineDao weldingMachineDao;

    @Autowired
    private WeldingDataDao weldingDataDao;

    /**
     * 品牌信息列表
     *
     * @param pageNo
     * @param pageSize
     * @param brandCode
     * @return
     */
    public PageData<BrandListVo> getBrandList(Integer pageNo, Integer pageSize, String brandCode) {
        PageData<BrandListVo> pageData = new PageData<>();

        QueryWrapper<WeldingMachineBrand> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(brandCode)) {
            wrapper.eq("brand_code", brandCode);
        }
        wrapper.ne("status", Constants.DELETE);

        IPage<BrandListVo> page = new Page<>(pageNo, pageSize);

        IPage<BrandListVo> pageRecords = weldingMachineBrandDao.queryBrandListPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(pageSize);
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());
        return pageData;
    }

    /**
     * 添加品牌
     *
     * @param addBrandListDto
     * @return
     */
    public MData addBrand(AddBrandDto addBrandListDto) {

        MData result = new MData();

        String brandCode = addBrandListDto.getBrandCode();
        WeldingMachineBrand group = getBrandByCode(brandCode);
        if (group != null) {
            return result.error("该品牌已存在");
        }

        WeldingMachineBrand produceGroup = new WeldingMachineBrand();
        produceGroup.setBrandCode(brandCode);
        produceGroup.setName(addBrandListDto.getName());
        produceGroup.setDescription(addBrandListDto.getDescription());
        produceGroup.setStatus(Constants.ACTIVE);

        weldingMachineBrandDao.insert(produceGroup);
        return result;
    }

    public WeldingMachineBrand getBrandByCode(String brandCode) {
        QueryWrapper<WeldingMachineBrand> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_code", brandCode);
        //是否需要处理删除状态。。todo
        return weldingMachineBrandDao.selectOne(wrapper);
    }

    public MData deleteBrand(DeleteBrandDto deleteBrandDto) {

        MData result = new MData();
        String brandCode = deleteBrandDto.getBrandCode();
        WeldingMachineBrand produceGroup = getBrandByCode(brandCode);
        if (produceGroup == null) {
            return result.error("brandCode有误");
        }
        weldingMachineBrandDao.deleteById(produceGroup);
        return result;
    }

    public PageData<ModelListVo> getModelList(Integer pageNo, Integer pageSize, String modelCode) {
        PageData<ModelListVo> pageData = new PageData<>();

        QueryWrapper<WeldingMachineModel> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(modelCode)) {
            wrapper.eq("l.model_code", modelCode);
        }
        wrapper.ne("l.status", Constants.DELETE);

        IPage<ModelListVo> page = new Page<>(pageNo, pageSize);

        IPage<ModelListVo> pageRecords = weldingModelDao.queryModelListPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(pageSize);
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;
    }

    public WeldingMachineModel getModelByCode(String modelCode) {
        QueryWrapper<WeldingMachineModel> wrapper = new QueryWrapper<>();
        wrapper.eq("model_code", modelCode);
        return weldingModelDao.selectOne(wrapper);
    }

    public MData addModel(AddModelDto addModelDto) {
        MData result = new MData();

        String brandCode = addModelDto.getModelCode();
        WeldingMachineModel group = getModelByCode(brandCode);
        if (group != null) {
            return result.error("该品牌已存在");
        }

        WeldingMachineModel produceGroup = new WeldingMachineModel();
        produceGroup.setModelCode(brandCode);
        produceGroup.setName(addModelDto.getName());
        produceGroup.setDescription(addModelDto.getDescription());
        produceGroup.setStatus(Constants.ACTIVE);
        produceGroup.setBrandName(addModelDto.getBrandName());

        weldingModelDao.insert(produceGroup);
        return result;
    }

    public MData deleteModel(DeleteModelDto deleteModelDto) {
        MData result = new MData();
        String brandCode = deleteModelDto.getModelCode();
        WeldingMachineModel produceGroup = getModelByCode(brandCode);
        if (produceGroup == null) {
            return result.error("modelCode有误");
        }
        weldingModelDao.deleteById(produceGroup);
        return result;
    }

    public PageData<MachineListVo> getMachineList(Integer pageNo, Integer pageSize, String machineCode, String address) {
        PageData<MachineListVo> pageData = new PageData<>();

        QueryWrapper<WeldingMachine> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(machineCode)) {
            wrapper.eq("m.machine_code", machineCode);
        }
        if (!StringUtils.isEmpty(address)) {
            wrapper.eq("m.address", address);
        }
        wrapper.ne("m.status", Constants.DELETE);

        IPage<MachineListVo> page = new Page<>(pageNo, pageSize);

        IPage<MachineListVo> pageRecords = weldingMachineDao.queryMachineListPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(pageSize);
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;


    }

    public MData addMachine(AddMachineDto addModelDto) {
        MData result = new MData();

        String machineCode = addModelDto.getMachineCode();
        WeldingMachine group = getMachineByCode(machineCode);
        if (group != null) {
            return result.error("该焊机已存在");
        }

        WeldingMachine produceGroup = new WeldingMachine();
        produceGroup.setMachineCode(machineCode);
        produceGroup.setName(addModelDto.getName());
        produceGroup.setDescription(addModelDto.getDescription());
        produceGroup.setStatus(Constants.ACTIVE);
        produceGroup.setBrandName(addModelDto.getBrandName());
        produceGroup.setModelName(addModelDto.getModelName());
        produceGroup.setCollectionCode(addModelDto.getCollectionCode());
        produceGroup.setNextMaintenance(addModelDto.getNextMaintenance());
        produceGroup.setAddress(addModelDto.getAddress());
        produceGroup.setMachineType(addModelDto.getMachineType());

        weldingMachineDao.insert(produceGroup);
        return result;
    }

    /**
     * 删除焊机
     *
     * @param deleteMachineDto
     * @return
     */
    public MData deleteMachine(DeleteMachineDto deleteMachineDto) {
        MData result = new MData();
        String machineCode = deleteMachineDto.getMachineCode();
        WeldingMachine group = getMachineByCode(machineCode);
        if (group == null) {
            return result.error("该焊机不存在");
        }
        weldingMachineDao.deleteById(group.getId());
        return result;
    }

    public WeldingMachine getMachineByCode(String machineCode) {
        QueryWrapper<WeldingMachine> wrapper = new QueryWrapper<>();
        wrapper.eq("model_code", machineCode);
        return weldingMachineDao.selectOne(wrapper);
    }

    public PageData<WeldingData> getMachineSyncData(MachineSyncDto machineSyncDto) {
        PageData<WeldingData> pageData = new PageData<>();

        QueryWrapper<WeldingData> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(machineSyncDto.getEquipCode())) {
            wrapper.eq("equip_code", machineSyncDto.getEquipCode());
        }
//        if (!StringUtils.isEmpty(address)) {
//            wrapper.eq("m.address", address);
//        }
        wrapper.orderByDesc("create_time");

        IPage<WeldingData> page = new Page<>(machineSyncDto.getPageNo(), machineSyncDto.getPageSize());

        IPage<WeldingData> pageRecords = weldingDataDao.queryPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(machineSyncDto.getPageSize());
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;
    }

    public PageData<WeldingData> getMachineSyncIndexData(MachineIndexDto machineSyncDto) {
        PageData<WeldingData> pageData = new PageData<>();

        QueryWrapper<WeldingData> wrapper = new QueryWrapper<>();
        IPage<WeldingData> page = new Page<>(machineSyncDto.getPageNo(), machineSyncDto.getPageSize());

        IPage<WeldingData> pageRecords = weldingDataDao.getMachineSyncIndexData(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(machineSyncDto.getPageSize());
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;
    }
}
