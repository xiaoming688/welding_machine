package com.welding.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.dao.pojo.MachineListVo;
import com.welding.model.WeldingMachine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeldingMachineDao extends BaseMapper<WeldingMachine> {

    @Select("select m.machine_code as machineCode, m.name as machineName, m.machine_type as machineType, " +
            "m.collection_code as collectionCode,  m.next_maintenance as nextMaintenance,  m.address, " +
            "b.name as brandName, n.name as modelName from welding_machine m " +
            "left join welding_machine_brand b on b.id = m.brand_id left join welding_machine_model n on n.id = m.model_id ${ew.customSqlSegment}")
    IPage<MachineListVo> queryMachineListPage(IPage<MachineListVo> page,
                                              @Param(Constants.WRAPPER) QueryWrapper<WeldingMachine> wrapper);
}
