package com.welding.web.netty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.welding.constants.Constants;
import com.welding.model.WeldingData;
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
            // 2.转换为统一出入平台报文
            Integer result = 0;
            if (weldingData.getWeldCode() != null && weldingData.getEquipCode() != null) {
                result = weldingDataService.addWeldingData(weldingData);
                List<WeldingData> requestData = new ArrayList<>();
                requestData.add(weldingData);
                // 3.发送http请求
                JSONObject requestResult = weldingDataService.syncDataRequest(requestData, Constants.SYNC_CURRENT);
                // 4.返回报文转换为dto
            }
            //等等处理业务
            return "处理成功！！！" + result;
        } catch (Exception e) {
            log.error("[交易处理异常]", e);
        }

        return null;
    }

    @Override
    public String getCode() {
        log.info("================6.2 code1的值为：" + 201 + "========================");
        return "201";
    }

}
