package com.welding.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.welding.constants.Constants;
import com.welding.model.SysRole;
import com.welding.model.WeldingData;
import com.welding.util.Http;
import com.welding.util.MData;
import com.welding.web.service.WeldingDataService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.StringEntity;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MM
 * @description
 * @create 2020-04-24 9:40
 **/
@Slf4j
@ApiOperation(value = "同步数据测试", notes = "")
@RestController
@RequestMapping(value = "/welding/test")
public class WeldingDataController {

    @Autowired
    private WeldingDataService weldingDataService;

    @ApiOperation(value = "获取实时数据", notes = "")
    @RequestMapping(value = "/getCurrentList", method = RequestMethod.GET)
    public MData getCurrentList() {
        MData result = new MData();
        List<WeldingData> dataList = weldingDataService.queryCurrentData();
        weldingDataService.syncDataRequest(dataList, "http://127.0.0.1:8082/welding/test/testCurrent");
        result.setData(dataList);

        return result;
    }

    @ApiOperation(value = "获取历史数据", notes = "")
    @RequestMapping(value = "/getHistoryList", method = RequestMethod.GET)
    public MData getHistoryList() {
        MData result = new MData();
        List<WeldingData> dataList = weldingDataService.getHistoryData();
        weldingDataService.syncDataRequest(dataList, "http://127.0.0.1:8082/welding/test/testHistory");
        result.setData(dataList);
        return result;
    }


    @ApiIgnore
    @RequestMapping(value = "/testCurrent", method = RequestMethod.POST)
    public MData testCurrent(@RequestBody Map<String, String> hjDatas) {
        MData result = new MData();
        result.put("result", 1);
//        result.put("result", 0);
        log.info("hjDatas current:{}", hjDatas);
        return result;
    }

    @ApiIgnore
    @RequestMapping(value = "/testHistory", method = RequestMethod.POST)
    public MData testHistory(@RequestBody Map<String, String> hjDatas) {
        MData result = new MData();
        result.put("result", 1);
//        result.put("result", 0);
        log.info("hjDatas history:{}", hjDatas);
        return result;
    }


}
