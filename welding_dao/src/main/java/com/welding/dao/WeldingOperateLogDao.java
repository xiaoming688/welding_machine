package com.welding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.welding.model.WeldingOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeldingOperateLogDao extends BaseMapper<WeldingOperateLog> {
}
