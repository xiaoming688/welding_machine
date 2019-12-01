package com.welding.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author MM
 * @create 2019-01-15 14:18
 **/
@Data
@Component
@PropertySource(value = {"classpath:${spring.profiles.active}/constants-${spring.profiles.active}.properties"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "session")
public class ShiroConstants {

    // Session超时时间，单位为毫秒（默认30分钟）
    private int expireTime;
    // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
    private int validationInterval;
    // 设置Cookie的域名
    private String domain;
    // 设置cookie的有效访问路径
    private String path;
    // 设置HttpOnly属性
    private boolean httpOnly;
    // 设置Cookie的过期时间，秒为单位
    private int cookieTimeout;
    // 登录地址
    private String loginUrl;
    // 权限认证失败地址
    private String unauthorizedUrl;
    //
    private String successUrl;
    private String anonUrl;
    private String logoutUrl;
    private String cookieName;
    private String kickOutUrl;

}
