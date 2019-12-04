package com.welding.web.config.shiro;

import com.welding.util.MData;
import com.welding.web.config.shiro.ShiroUtils;
import com.welding.web.util.ServletUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author MM
 * @create 2019-11-28 16:08
 **/
public class BasicAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();

        Session session = subject.getSession();

        // 默认没有subject.isRemembered() 的条件，加上之后，兼容rememberMe的情况
        return super.isAccessAllowed(request, response, mappedValue) || subject.isRemembered();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        //判断是不是Ajax请求
        if (ServletUtils.isAjaxRequest(httpRequest)) {
            System.out.println("is json request time denied");
            MData map = new MData();
            map.status("401");
            map.error("未登录");
            ShiroUtils.outMData(response, request, map);
        }
        MData resultMap = new MData();
        resultMap.put("status", "401");
        resultMap.put("message", "未登录！");
        ShiroUtils.outMData(response, request, resultMap);
        return false;
    }

}
