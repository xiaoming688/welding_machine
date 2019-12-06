package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.dao.pojo.ProduceGroupListVo;
import com.welding.dao.pojo.ProduceParentGroupVo;
import com.welding.model.WeldingProduceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WeldingProduceGroupDao extends BaseMapper<WeldingProduceGroup> {

    @Select("select l.leader_user_name as userName, l.group_name as groupName, " +
            "l.group_type as groupType, l.email, l.address from welding_produce_group l " +
            "${ew.customSqlSegment}")
    IPage<ProduceGroupListVo> queryGroupListPage(IPage<ProduceGroupListVo> page,
                                                 @Param(Constants.WRAPPER) QueryWrapper<WeldingProduceGroup> wrapper);

    @Select("select l.group_name as groupName, l.id as groupId from welding_produce_group l where l.parent_id=0")
    List<ProduceParentGroupVo> getParentGroup();
}
