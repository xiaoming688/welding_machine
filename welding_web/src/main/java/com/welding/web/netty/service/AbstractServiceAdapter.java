package com.welding.web.netty.service;

import cn.hutool.core.date.DateUtil;
import com.welding.model.WeldingData;
import com.welding.web.netty.context.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MM
 */
@Slf4j
public abstract class AbstractServiceAdapter implements IMessageService<String> {


    @Autowired
    private MessageContext messageContext;


    /**
     * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化。此方法必须在将类放入服务之前调用
     */
    @PostConstruct
    public void register() {
        log.info("================6.1 依赖注入完整后需要执行的方法：获取code值（根据code值判定处理不同的业务逻辑） ！！========================");
        messageContext.register(getCode(), this);
    }

    /**
     * &&#01GDNGG4C-04-MYC085+008-HN01-SB-LW#02HN01#03YDXB-WPS-13-607#
     * &&HN-05AA15225601060AAAAAA
     * P:CCW #L:RW #C:0#V:00.1#T:0.0#A:0#D:2020-8-2 13:00:25
     *
     * @param body
     * @return
     */
    protected WeldingData getTemplate(String body) {
        WeldingData result = new WeldingData();
        try {
            log.info("处理 body  {}", body);
            //结束加个符号方便解析
            String line1 = "#01(.*?)#02(.*?)#03(.*?)#";
            String line2 = ".*&&(.*?)AA";
            String line3 = ".*&&(.*?)&(\\d+)";
            String line4 = "P:(.*?)#L:(.*?)#C:(.*?)#V:(.*?)#T:(.*?)#A:(.*?)#D:(.*)";
            //先解第一行
            Matcher matcher = getMatcher(line1, body);
            if (matcher.find()) {
                result.setWeldCode(matcher.group(1));
                result.setTeamCode(matcher.group(2));
                result.setProcess(matcher.group(3));
            } else {
                log.info("第一行数据解析有误: {}", body);
            }
            //第二行PERSON_CODE
            matcher = getMatcher(line2, body);
            if (matcher.find()) {
                result.setPersonCode(matcher.group(1));
            } else {
                log.info("第二行数据解析有误: {}", body);
            }

            //第三行PERSON_CODE
            matcher = getMatcher(line3, body);
            if (matcher.find()) {
                result.setEquipCode(matcher.group(1) + "+" + matcher.group(2));
            } else {
                log.info("第二行数据解析有误: {}", body);
            }

            //第四行PERSON_CODE
            matcher = getMatcher(line4, body);
            if (matcher.find()) {
                result.setPosition(matcher.group(1));
                result.setLayer(matcher.group(2));
                result.setCurrent(matcher.group(3));
                result.setVoltage(matcher.group(4));
                result.setTemp(matcher.group(5));
                result.setAngle(matcher.group(6));
                result.setCjTime(matcher.group(7));
//                result.setCjTime(DateUtil.formatDateTime(new Date()));
            } else {
                log.info("第四行数据解析有误: {}", body);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            return null;
        }
        return result;
    }

    public Matcher getMatcher(String reg, String value) {
        Pattern pattern = Pattern.compile(reg);
        // 内容 与 匹配规则 的测试
        return pattern.matcher(value);
    }
}
