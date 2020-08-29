package com.welding.web.netty.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.welding.util.SpringContext;
import com.welding.web.netty.context.MessageContext;
import com.welding.web.netty.service.IMessageService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端触发操作
 *
 * @author MM
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * channelAction
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("================13. 通过连接已建立中....========================");
    }

    /**
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("--------Netty Disconnect Client IP is :" + ctx.channel().id().asShortText() + " "
                + ctx.channel().remoteAddress() + "--------");
        log.info("================9========================");
        ctx.close();
    }

    /**
     * 切分信息的方法
     *
     * @param msgBytes
     * @return
     */
    private List<byte[]> getMsgList(byte[] msgBytes) {
        List<byte[]> list = new ArrayList<>();
        //具体业务代码略
        return list;
    }

    /**
     * 功能：读取服务器发送过来的信息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("================14. 通道读取服务器发送过来的信息========================");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        String bodyGBK = new String(req, "GBK");

        //解析json
        JSONObject jsonObject = null;
        try {
            String end = body.substring(body.length() - 1);
            if (!end.equals("}")) {
                log.info("error msg!!!!" + end.toCharArray());
//                return;
            }
            jsonObject = JSON.parseObject(body);
            log.info("解析完成：" + jsonObject.toString());
            //这里可以写业务代码
        } catch (Exception e) {
            log.info("请注意，报文异常！" + e.getMessage());
        } finally {
            //释放msg 不然可能会导致内存溢出 netty操作的应该是直接内存
            ReferenceCountUtil.release(msg);
        }


        log.info("[请求报文 UTF-8：][" + body + "]");
        log.info("[请求报文 GBK：][" + bodyGBK + "]");

        handleMessage(ctx, body);
    }

    private void handleMessage(ChannelHandlerContext ctx, String body) {
        log.info("================15. 业务逻辑处理开始========================");
        MessageContext messageContext = (MessageContext) SpringContext.getBean("messageContext");
        //可以根据业务类型选择不同的service
        String type = "201";
        IMessageService<String> service = messageContext.get(type);
        String resXml = (String) service.handle(type, body);

        log.info("================18. 返回给Socket请求客户端的处理结果为：" + resXml + "========================");
        ctx.writeAndFlush(Unpooled.copiedBuffer(resXml.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("================17. Netty读取信息已经完成啦！！========================");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("--------Netty Exception ExceptionCaught :" + ctx.channel().id().asShortText() + " "
                + cause.getMessage() + "=======================", cause);
        ctx.close();
    }
}
