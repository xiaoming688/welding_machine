package com.welding.web.controller;

import com.welding.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.Socket;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @PostMapping(value = "/socket/init")
    @ResponseBody
    public String socketTest(HttpServletRequest request, String code) {
        log.info("================10. http请求开始！！========================");
        String reqMsg = getReqString(request);
        Socket socket = null;
        PrintWriter pw = null;
        DataInputStream is = null;
        String retMsg = StringUtils.EMPTY;
        try {
            socket = new Socket("127.0.0.1", Constants.NETTY_SERVER_PORT);
            pw = new PrintWriter(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
            // 发送数据
            log.info("================11. 发送Socket数据开始！！========================");
            pw.println(reqMsg);
            log.info("================12. 发送Socket数据结束！！========================");
            pw.flush();
            socket.shutdownOutput();
            int len;
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            retMsg = sb.toString();
            log.info("================19. 接受Socket服务端返回处理数据内容为：" + retMsg + "========================");
        } catch (Exception e) {
            log.error("客户端代码发送失败!", e);
            retMsg = "请求处理发生异常，请稍后重试!";
        } finally {
            try {
                if(is!=null){
                    is.close();
                }
                if(pw!=null){
                    pw.close();
                }
                if(socket!=null){
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
        return retMsg;
    }

    private String getReqString(HttpServletRequest req) {
        log.info("[获取请求报文内容][开始]");

        String reqXml = "";
        // 输入流
        InputStream inputStream = null;
        // 输出流
        ByteArrayOutputStream outputStream = null;

        try {
            inputStream = req.getInputStream();
            outputStream = new ByteArrayOutputStream();
            // 创建缓冲区，大小1024字节
            byte[] buffer = new byte[1024];
            int len = -1;
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
            reqXml = new String(outputStream.toByteArray(), "UTF-8");
        } catch (IOException e) {
            log.error("[获取请求报文内容][发生异常][异常为：" + e.getMessage() + "]" + e);
        } finally {
            // 关闭输入流
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
            // 关闭输出流
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            inputStream = null;
            outputStream = null;
        }
        log.info("[获取请求报文内容][报文内容为：" + reqXml + "]");
        log.info("[获取请求报文内容][结束]");
        return reqXml;
    }

}
