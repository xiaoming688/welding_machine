package com.welding.web.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MM
 * @description
 * @create 2020-08-31 17:25
 **/
@Slf4j
public class HeatBeatHandler extends ChannelInboundHandlerAdapter {
    @Override//用户事件触发
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //如果接收到的事件消息属于我们之前定义的心跳事件
        if (evt instanceof IdleStateEvent) {
            //将该事件消息强转为心跳事件
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            //这里虽然监听了三种空闲,但是我们只对读写空闲做操作
            //如果是读空闲
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("读空闲事件触发...");
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("写空闲事件触发...");
            } else if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                log.info("===============================================");
                log.info("读写空闲事件触发，关闭该通道资源");
                ctx.channel().close();//关闭该通道
            }
        }
    }
}
