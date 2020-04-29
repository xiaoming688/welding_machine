package com.welding.web.netty.context;

import com.welding.web.netty.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MM
 */
@Slf4j
@Component
public class MessageContext {


    private Map<String, IMessageService<String>> handlers = new HashMap<>();

    public void register(String type, IMessageService<String> handler) {
        log.info("================6.3 type和service以键值对的方式放入handlers map中========================");
        handlers.put(type, handler);
    }

    public IMessageService<String> get(String type) {
        log.info("================16. 根据type获取相应的Service========================");
        return handlers.get(type);
    }

}
