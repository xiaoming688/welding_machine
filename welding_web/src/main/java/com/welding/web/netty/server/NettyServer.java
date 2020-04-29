
package com.welding.web.netty.server;

import com.welding.constants.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * socket监听服务
 * @author MM
 */
@Slf4j
public class NettyServer implements Runnable {

    /**
     * socket监听
     */
    public static void soketListener() {
    	log.info("================5.NettyServer开始启动========================");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        int port = Constants.NETTY_SERVER_PORT;
        try {
            log.info("================7.1 NettyServer 端口为：" + port + "========================");
            ServerBootstrap bootstrap = new ServerBootstrap();
            //绑定线程池
            bootstrap.group(bossGroup, workerGroup)
                    // 指定使用的channel
                .channel(NioServerSocketChannel.class)  
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 65535, 65535))
                    // 绑定客户端连接时候触发操作
                .childHandler(new ChannelInitializer<SocketChannel>() { 
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    log.info("================7.2 报告，有个socket客户端链接到本服务器, IP为：" + ch.localAddress().getHostName() + ", Port为：" + ch.localAddress().getPort() + "========================");
                    ch.pipeline().addLast(new ServerHandler()); // 客户端触发操作
                }
            });
            log.info("================8.NettyServer 启动中.....========================");
            // 服务器异步创建绑定
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("================9.NettyServer 启动完毕！！========================");
            // 关闭服务器通道
            channelFuture.channel().closeFuture().sync();
            log.info("================41.NettyServer 关闭服务器通道！！========================");
        } catch (Exception e) {
        	log.error("================4.1 NettyServer 端口为：" + port + " 启动出现异常， 异常内容为：" + e.getMessage() + "========================");
        } finally {
        	log.error("================4.2 NettyServer 服务关闭========================");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * @see Runnable#run()
     */
    @Override
    public void run() {
        log.info("================4.多线程启动Netty Server========================");
        NettyServer.soketListener();
    }

}
