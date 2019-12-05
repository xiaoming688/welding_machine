package com.welding.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.dao.pojo.SysLogVo;
import com.welding.dao.pojo.UserListVo;
import com.welding.model.SysUser;
import com.welding.model.WeldingOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WeldingOperateLogDao extends BaseMapper<WeldingOperateLog> {


    @Select("select u.account_no as accountNo, u.user_name as userName, l.id as logId, l.log_type as logType, " +
            "l.operate_time as operateTime, l.operate_ip as operateIp from welding_operate_log l " +
            "left join sys_user u on u.id = l.user_id ${ew.customSqlSegment}")
    IPage<SysLogVo> queryLogPage(IPage<SysLogVo> page, @Param(Constants.WRAPPER) QueryWrapper<WeldingOperateLog> wrapper);
}
