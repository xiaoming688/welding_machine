package com.welding.web.service;

import com.welding.dao.WeldingOperateLogDao;
import com.welding.model.WeldingOperateLog;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
