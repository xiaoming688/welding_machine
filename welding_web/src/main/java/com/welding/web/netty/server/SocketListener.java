
package com.welding.web.netty.server;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author MM
 */
@Slf4j
@WebListener
public class SocketListener implements ServletContextListener {

    private NettyServer server;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("================1.启动时，开启监听========================");
        if (server == null) {
            log.info("================2.启动时，NettyServer为null，启动Netty服务========================");
            Thread thread = new Thread(new NettyServer());
            thread.start();
        }
    }

    // 应用关闭时，此方法被调用
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("================23 netty closed =======================");
    }

}
