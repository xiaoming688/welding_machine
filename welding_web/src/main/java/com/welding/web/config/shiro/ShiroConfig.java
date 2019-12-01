package com.welding.web.config.shiro;

import com.welding.constants.ShiroConstants;
import com.welding.web.config.BasicAuthenticationFilter;
import com.welding.web.config.CustomRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author MM
 * @create 2019-11-28 15:48
 **/
@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroConstants shiroConstants;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 设置免认证 url
//        String[] anonUrls = StringUtils.splitArrayElementsIntoProperties(shiroConstants.getAnonUrl(), ",");
//        for (String url : anonUrls) {
//            filterChainDefinitionMap.put(url, "anon");
//        }
        filterChainDefinitionMap.put("/user/userLogin", "anon");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //自定义authc filter
        shiroFilterFactoryBean.getFilters().put(DefaultFilter.authc.name(), new BasicAuthenticationFilter());
        return shiroFilterFactoryBean;
    }

    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    /**
     * 生成一个ShiroRedisCacheManager
     *
     * @return
     **/
    public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplateJdk) {
        return new ShiroRedisCacheManager(redisTemplateJdk);
    }


    /**
     * Session Manager：会话管理
     * 即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；
     * 会话可以是普通JavaSE环境的，也可以是如Web环境的；
     */
    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        SimpleCookie simpleCookie = new SimpleCookie(shiroConstants.getCookieName());
        simpleCookie.setDomain(shiroConstants.getDomain());
        sessionManager.setSessionIdCookie(simpleCookie);

        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
//        enterpriseCacheSessionDAO.setCacheManager(cacheManager);
//        enterpriseCacheSessionDAO.setActiveSessionsCacheName("aaaaa");
        sessionManager.setSessionDAO(enterpriseCacheSessionDAO);
        //设置session过期时间
        sessionManager.setGlobalSessionTimeout(shiroConstants.getExpireTime());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(shiroConstants.getValidationInterval());
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(CustomRealm customRealm, RedisTemplate<Object, Object> redisTemplateJdk, SessionManager sessionManager) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRememberMeManager(rememberMeManager());
        defaultSecurityManager.setCacheManager(cacheManager(redisTemplateJdk));
        defaultSecurityManager.setSessionManager(sessionManager);
        defaultSecurityManager.setRealm(customRealm);
        return defaultSecurityManager;
    }

    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie 加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));

        return cookieRememberMeManager;
    }
    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
        // 这里的Cookie的默认名称是 CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME
//        SimpleCookie cookie = new SimpleCookie(shiroConstants.getCookieName());
        SimpleCookie cookie = new SimpleCookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
        // 是否只在https情况下传输
        cookie.setSecure(false);
        cookie.setDomain(shiroConstants.getDomain());
        cookie.setPath(shiroConstants.getPath());
        // 设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(shiroConstants.getCookieTimeout());
        return cookie;
    }



    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}
