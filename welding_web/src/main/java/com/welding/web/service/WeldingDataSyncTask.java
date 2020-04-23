package com.welding.web.service;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class WeldingDataSyncTask {



    /**
     * 每天固定时间
     */
//    @Scheduled(cron = "0 1 0 * * ?")
    @Scheduled(cron = "0/4 * * * * ? ")
    public void checkDesignerAccount() {
        log.info("current date: {}", DateUtil.now());
    }
}
