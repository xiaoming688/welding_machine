package com.welding.web.controller;

import com.welding.constants.Constants;
import com.welding.util.MData;
import com.welding.util.Page;
import com.welding.web.pojo.GetSysLogListDto;
import com.welding.dao.pojo.SysLogVo;
import com.welding.web.service.SysOperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MM
 * @create 2019-12-03 14:23
 **/
@Api(tags = "系统管理", description = "")
@Slf4j
@RestController
@RequestMapping("/welding")
public class SystemManageController {

    @Autowired
    private SysOperateLogService sysOperateLogService;

    /**
     * 获取系统日志
     *
     * @param getSysLogListDto
     * @return
     */
    @ApiOperation(value = "获取系统日志", notes = "")
    @RequestMapping(value = "/getSysLogList", method = RequestMethod.POST)
    public MData getSysLogList(@RequestBody GetSysLogListDto getSysLogListDto) {

        MData result = new MData();
        Integer pageNo = getSysLogListDto.getPageNo() == null
                ? Constants.DEFAULT_PAGE_NO : getSysLogListDto.getPageNo();
        Integer pageSize = getSysLogListDto.getPageSize() == null
                ? Constants.DEFAULT_PAGE_SIZE : getSysLogListDto.getPageSize();
        String logType = getSysLogListDto.getLogType();

        Page<SysLogVo> pageData = sysOperateLogService.getSysOperateLogData(pageNo, pageSize, logType);

        result.setData(pageData);

        return result;
    }

}
