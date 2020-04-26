package com.welding.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.welding.dao.WeldingOperateLogDao;
import com.welding.model.WeldingOperateLog;
import com.welding.util.PageData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.dao.pojo.SysLogVo;
import com.welding.web.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author MM
 * @create 2019-12-04 10:31
 **/
@Service
public class SysOperateLogService {

    @Autowired
    private WeldingOperateLogDao weldingOperateLogDao;


    public void addLoginLog(String logType, Integer userId){

        WeldingOperateLog operateLog = new WeldingOperateLog();
        operateLog.setUserId(userId);
        operateLog.setLogType(logType);
        operateLog.setOperateIp(ShiroUtils.getIp());
        operateLog.setOperateTime(new Date());
        operateLog.setOperateRoute(ServletUtils.getRequest().getServletPath());

        weldingOperateLogDao.insert(operateLog);
    }

    public PageData<SysLogVo> getSysOperateLogData(Integer pageNo, Integer pageSize, String logType){
        PageData<SysLogVo> pageData = new PageData<>();

        QueryWrapper<WeldingOperateLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(logType)) {
            wrapper.eq("l.log_type", logType);
        }
        IPage<SysLogVo> page = new Page<>(pageNo, pageSize);

        IPage<SysLogVo> pageRecords =  weldingOperateLogDao.queryLogPage(page, wrapper);
        pageData.setData(pageRecords.getRecords());
        pageData.setSize(pageSize);
        pageData.setPage(Long.valueOf(pageRecords.getCurrent()).intValue());
        pageData.setTotal(Long.valueOf(pageRecords.getTotal()).intValue());

        return pageData;
    }



}
