package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.dao.pojo.BrandListVo;
import com.welding.dao.pojo.ModelListVo;
import com.welding.dao.pojo.ProduceGroupListVo;
import com.welding.model.WeldingMachineBrand;
import com.welding.model.WeldingMachineModel;
import com.welding.model.WeldingProduceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author MM
 * @description
 * @create 2020-04-26 9:57
 **/
@Mapper
@Repository
public interface WeldingMachineBrandDao extends BaseMapper<WeldingMachineBrand> {

    @Select("select l.brand_code as brandCode, l.name as name, " +
            "l.description as description from welding_machine_brand l " +
            "${ew.customSqlSegment}")
    IPage<BrandListVo> queryBrandListPage(IPage<BrandListVo> page,
                                          @Param(Constants.WRAPPER) QueryWrapper<WeldingMachineBrand> wrapper);


}
