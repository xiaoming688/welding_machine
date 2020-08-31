package com.welding.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.welding.constants.Constants;
import com.welding.dao.WeldingDataDao;
import com.welding.model.SysUserRole;
import com.welding.model.WeldingData;
import com.welding.util.Http;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MM
 * @description
 * @create 2020-04-24 9:24
 **/
@Slf4j
@Service
public class WeldingDataService {

    @Resource
    private WeldingDataDao weldingDataDao;

    public Integer addWeldingData(WeldingData data) {
        if (data != null) {
            return weldingDataDao.insert(data);
        }
        return -1;
    }


    public List<WeldingData> queryCurrentData() {
        QueryWrapper<WeldingData> wrapper = new QueryWrapper<>();
        wrapper.eq("is_history", Constants.SYNC_NOT_IS_HISTORY);
        wrapper.eq("upload_status", Constants.SYNC_INIT);
        return weldingDataDao.selectList(wrapper);
    }

    public List<WeldingData> getHistoryData() {
        QueryWrapper<WeldingData> wrapper = new QueryWrapper<>();
        wrapper.eq("is_history", Constants.SYNC_IS_HISTORY);
        wrapper.ne("upload_status", Constants.SYNC_SUCCESS);
        return weldingDataDao.selectList(wrapper);
    }


    public JSONObject syncDataRequest(List<WeldingData> dataList, String url) {
        try {
            Http http = new Http(url);

            List<Map<String, String>> paramList = new ArrayList<>();
            for (WeldingData weldingData : dataList) {
                Map<String, String> param = new HashMap<>();
                param.put("TEAM_CODE", weldingData.getTeamCode());
                param.put("PROCESS", weldingData.getProcess());
                param.put("EQUIP_CODE", weldingData.getEquipCode());
                param.put("HJ_PROCESS", weldingData.getHjProcess());

                param.put("PERSON_CODE", weldingData.getPersonCode());
                param.put("WELD_CODE", weldingData.getWeldCode());
                param.put("POSITION", weldingData.getPosition());
                param.put("LAYER", weldingData.getLayer());
                param.put("CURRENT", weldingData.getCurrent());

                param.put("VOLTAGE", weldingData.getVoltage());
                param.put("SS_SPEED", weldingData.getSsSpeed());
                param.put("HJ_SPEED", weldingData.getHjSpeed());
                param.put("TEMP", weldingData.getTemp());

                param.put("HUMIDITY", weldingData.getHumidity());
                param.put("CJ_TIME", weldingData.getCjTime());

                paramList.add(param);
            }

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("hjDatas", paramList);

            JSONObject parseObject = JSONObject.parseObject(JSON.toJSONString(requestMap));
            log.info("parseObject:" + parseObject);
            StringEntity stringEntity = new StringEntity(parseObject.toString(), StandardCharsets.UTF_8);
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json;charset=UTF-8");

            Http.HttpResult r = http.doPost(stringEntity);
            if (r == null) {
                return null;
            }
            log.info("result:" + r);
            JSONObject jsonResult = r.toJsonObject();
            //同步成功。。
            if (jsonResult.get("result") != null && String.valueOf(jsonResult.get("result")).equals("0")) {
                updateWeldingDataStatus(dataList, Constants.SYNC_SUCCESS);
            } else {
                updateWeldingDataHistory(dataList, Constants.SYNC_IS_HISTORY);
            }
            return jsonResult;
        } catch (Exception e) {
            log.error(e.toString(), e);
            //异常后这批数据用重传
            updateWeldingDataHistory(dataList, Constants.SYNC_IS_HISTORY);
        }
        return null;
    }


    public void updateWeldingDataStatus(List<WeldingData> updateList, String status) {
        if (updateList.isEmpty()) {
            return;
        }
        for (WeldingData weldingData : updateList) {
            weldingData.setUploadStatus(status);
        }
        weldingDataDao.updateStatusBatch(updateList);
    }

    public void updateWeldingDataHistory(List<WeldingData> updateList, Integer isHistory) {
        if (updateList.isEmpty()) {
            return;
        }
        for (WeldingData weldingData : updateList) {
            weldingData.setIsHistory(isHistory);
        }
        weldingDataDao.updateHistoryBatch(updateList);
    }

}
