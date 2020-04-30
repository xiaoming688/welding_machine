package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.dao.pojo.ModelListVo;
import com.welding.model.WeldingMachineBrand;
import com.welding.model.WeldingMachineModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author MM
 * @description
 * @create 2020-04-26 10:42
 **/
@Mapper
@Repository
public interface WeldingModelDao extends BaseMapper<WeldingMachineModel> {

    @Select("select l.model_code as modelCode, l.name as name, l.brand_name as brandName, " +
            "l.description as description from welding_machine_model l ${ew.customSqlSegment}")
    IPage<ModelListVo> queryModelListPage(IPage<ModelListVo> page,  @Param(Constants.WRAPPER) QueryWrapper<WeldingMachineModel> wrapper);
}
