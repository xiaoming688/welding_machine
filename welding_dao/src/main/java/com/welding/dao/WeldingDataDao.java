package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.welding.model.WeldingData;
import com.welding.model.WeldingProcessDic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MM
 * @description
 * @create 2020-04-24 9:24
 **/
@Mapper
@Repository
public interface WeldingDataDao extends BaseMapper<WeldingData> {

    @Update("<script>" +
            "<foreach collection = 'obj' item ='item' open='' close='' separator=';'>" +
            "update welding_data set upload_status =#{item.uploadStatus} " +
            "where id =#{item.id}" +
            "</foreach></script>")
    int updateStatusBatch(@Param("obj") List<WeldingData> objs);

    @Update("<script>" +
            "<foreach collection = 'obj' item ='item' open='' close='' separator=';'>" +
            "update welding_data set is_history =#{item.isHistory} " +
            "where id =#{item.id}" +
            "</foreach></script>")
    int updateHistoryBatch(@Param("obj") List<WeldingData> objs);

    @Select("select * from welding_process_dic")
    List<WeldingProcessDic> queryProcessDics();

    @Select("select * from welding_data")
    IPage<WeldingData> queryPage(IPage<WeldingData> page,
                                 @Param(Constants.WRAPPER) QueryWrapper<WeldingData> wrapper);

    @Select("SELECT a.* FROM (SELECT * FROM welding_data ORDER BY create_time desc) a  GROUP BY a.equip_code")
    IPage<WeldingData> getMachineSyncIndexData(IPage<WeldingData> page,
                                               @Param(Constants.WRAPPER) QueryWrapper<WeldingData> wrapper);
}
