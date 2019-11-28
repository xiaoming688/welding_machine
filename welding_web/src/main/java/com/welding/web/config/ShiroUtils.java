package com.welding.web.config;

import com.alibaba.fastjson.JSONObject;
import com.welding.util.MData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * shiro 工具类
 *
 * @author MM
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubject().logout();
    }


//    public static void setSysUser(LoginUser user) {
//        Subject subject = getSubject();
//        PrincipalCollection principalCollection = subject.getPrincipals();
//        String realmName = principalCollection.getRealmNames().iterator().next();
//        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
//        // 重新加载Principal
//        subject.runAs(newPrincipalCollection);
//    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }

    /**
     * response 输出JSON
     *
     * @param resultMap
     */
    public static void outMData(ServletResponse response, ServletRequest request, MData resultMap) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

            out = httpResponse.getWriter();
            out.write(JSONObject.toJSON(resultMap).toString());
        } catch (Exception e) {
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
