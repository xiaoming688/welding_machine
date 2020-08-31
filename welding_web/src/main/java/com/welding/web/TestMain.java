package com.welding.web;

import cn.hutool.core.date.DateUtil;
import com.welding.web.config.shiro.ShiroUtils;
//import com.welding.web.netty.test.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "59987"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public void testSocket() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(HOST, PORT))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            //p.addLast(new LoggingHandler(LogLevel.INFO));
//                        p.addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture channelFuture = b.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String reg1 = "#01(.*?)#02(.*?)#03(.*?)#";
        String reg2 = ".*&&(.*?)AA";
        String reg3 = ".*&&(.*?)&(\\d+)";
        String reg4 = "P:(.*?)#L:(.*?)#C:(.*?)#V:(.*?)#T(.*?)#A:(.*?)#D:(.*)";
        String value = "&&#01GDNGG4C-04-MYC085+008-HN01-SB-LW#02HN01#03YDXB-WPS-13-607#^M" +
                "&&HN-05AA15225601060AAAAAA^M" +
                "&&F500GD&18120327041^M " +
                "P:CCW #L:RW #C:0#V:00.0#T:0.0#A:63#D:2020-9-23 12:21:42";
        System.out.println(value);
        Pattern pattern1 = Pattern.compile(reg1);
        // 内容 与 匹配规则 的测试
        Matcher matcher1 = pattern1.matcher(value);

        Pattern pattern2 = Pattern.compile(reg2);
        // 内容 与 匹配规则 的测试
        Matcher matcher2 = pattern2.matcher(value);

        Pattern pattern3 = Pattern.compile(reg3);
        // 内容 与 匹配规则 的测试
        Matcher matcher3 = pattern3.matcher(value);

        Pattern pattern4 = Pattern.compile(reg4);
        // 内容 与 匹配规则 的测试
        Matcher matcher4 = pattern4.matcher(value);
        if (matcher1.find()) {
            // 包含前后的两个字符
//            System.out.println(matcher1.group());
            System.out.println("第一行++++++++++++");
            System.out.println(matcher1.group(1));
            System.out.println(matcher1.group(2));
            System.out.println(matcher1.group(3));
        } else {
            System.out.println(" 没有匹配到内容....");
        }
        if (matcher2.find()) {
            // 包含前后的两个字符
            System.out.println("第二行++++++++++++");
            System.out.println(matcher2.group(1));
        } else {
            System.out.println(" 没有匹配到内容....");
        }
        if (matcher3.find()) {
            // 包含前后的两个字符
            System.out.println("第三行++++++++++++");
//            System.out.println(matcher2.group());
            System.out.println(matcher3.group(1));
            System.out.println(matcher3.group(2));
        } else {
            System.out.println(" 没有匹配到内容....");
        }
        if (matcher4.find()) {
            // 包含前后的两个字符
            System.out.println("第四行++++++++");
//            System.out.println(matcher4.group());
            System.out.println(matcher4.group(1));
            System.out.println(matcher4.group(2));
            System.out.println(matcher4.group(3));
            System.out.println(matcher4.group(4));
            System.out.println(matcher4.group(5));
            System.out.println(matcher4.group(6));
            System.out.println(matcher4.group(7));
        } else {
            System.out.println(" 没有匹配到内容....");
        }
    }

}
