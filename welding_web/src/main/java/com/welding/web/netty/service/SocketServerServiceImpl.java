package com.welding.web.netty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.welding.constants.Constants;
import com.welding.model.WeldingData;
import com.welding.model.WeldingProcessDic;
import com.welding.util.StringUtil;
import com.welding.web.service.WeldingDataService;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接受数据业务处理类
 *
 * @author MM
 */
@Slf4j
@Service
public class SocketServerServiceImpl extends AbstractServiceAdapter {

    @Autowired
    private WeldingDataService weldingDataService;


    @Override
    public String handle(String type, String message) {
        log.info("================15. 进去{}对应的Service——SocketServerService ========================", type);
        try {
            // 1.读取配置中心模板
            WeldingData weldingData = getTemplate(message);
            weldingData.setProjectId(Constants.DEFAULT_PROJECTID);
            weldingData.setReformUnit(Constants.DEFAULT_REFURMUNIT);
            weldingData.setHjProcess(getHjProcess(weldingData.getProcess()));
            weldingData.setWarn(getWarn(weldingData));
            weldingData.setRequestData(message);
            // 2.转换为统一出入平台报文
            Integer result = 0;
            if (weldingData.getWeldCode() != null && weldingData.getEquipCode() != null) {
                result = weldingDataService.addWeldingData(weldingData);
                List<WeldingData> requestData = new ArrayList<>();
                requestData.add(weldingData);
                // 3.发送http请求
//                JSONObject requestResult = weldingDataService.syncDataRequest(requestData, Constants.SYNC_CURRENT);
                // 4.返回报文转换为dto
            }
            //等等处理业务
            log.info("处理成功：{}", result);
            return "success";
        } catch (Exception e) {
            log.error("[交易处理异常]", e);
        }

        return null;
    }

    /**
     * 电压:
     * 根焊不得超过36伏，
     * 填充不得超过23伏，
     * 盖面不得超过23伏
     * 电流:
     * 根焊不得超过110安，
     * 填充不得超过260安，
     * 盖面不得超过260安
     * <p>
     * * 异常信息说明：WARN=0,无异常；WARN=1,电流异常；WARN=2,电压异常；WARN=5,送丝速度异常；
     * * WARN=10,焊速异常； WARN=3,电流、电压异常；WARN=6,电流、送丝速度异常；
     * * WARN=11,电流、焊速异常；WARN=7,电压、送丝速度异常；WARN=12,电压、焊速异常；
     * * WARN=15,送丝速度、焊速异常；WARN=8,电流、电压、送丝速度异常；WARN=13,电流、电压、焊速异常；
     * * WARN=16,电流、送丝速度、焊速异常；WARN=17,电压、送丝速度、焊速异常；
     * * WARN=18,电流、电压、送丝速度、焊速异常；
     *
     * @param data
     * @return
     */
    private String getWarn(WeldingData data) {
        String voltage = StringUtil.isBlank(data.getVoltage()) ? "0" : data.getVoltage();
        String current = StringUtil.isBlank(data.getCurrent()) ? "0" : data.getCurrent();
        if (StringUtil.isNotBlank(data.getLayer())) {
            //根焊
            if (data.getLayer().contains("RW")) {
                return getWarnResult(voltage, "36", current, "110");
            } else {
                return getWarnResult(voltage, "23", current, "260");
            }
        }
        return "0";
    }

    private String getWarnResult(String voltage, String limitVoltage, String current, String limitCurrent) {
        if (voltage.compareTo(limitVoltage) > 0 && current.compareTo(limitCurrent) > 0) {
            return "3";
        }
        if (voltage.compareTo(limitVoltage) > 0) {
            return "2";
        }
        if (current.compareTo(limitCurrent) > 0) {
            return "1";
        }
        return "0";
    }

    private String getHjProcess(String process) {
        if (process == null || "".equals(process)) {
            return "";
        }
        List<WeldingProcessDic> processDics = weldingDataService.getHjprocessDic();
        for (WeldingProcessDic processDic : processDics) {
            if (process.contains(processDic.getProcessName())) {
                return processDic.getWeldingMethod();
            }
        }
        return "";
    }

    @Override
    public String getCode() {
        log.info("================6.2 code1的值为：" + 201 + "========================");
        return "201";
    }

}
