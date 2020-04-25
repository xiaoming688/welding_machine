package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.dao.pojo.ClassGroupListVo;
import com.welding.dao.pojo.ProduceGroupListVo;
import com.welding.model.WeldingClassGroup;
import com.welding.model.WeldingProduceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeldingClassGroupDao extends BaseMapper<WeldingClassGroup> {


    IPage<ClassGroupListVo> queryGroupListPage(IPage<ClassGroupListVo> page,
                                               @Param(Constants.WRAPPER) QueryWrapper<WeldingClassGroup> wrapper);
}
