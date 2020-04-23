package com.welding.web.config.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author MM
 * @create 2019-01-15 14:38
 **/
@Component
public class ShiroRedisCacheManager extends AbstractCacheManager {

    @Autowired
    private ShiroRedisCache shiroRedisCache;


    @Override
    protected Cache createCache(String s) throws CacheException {
        shiroRedisCache.setPrefix(s);
//        return new ShiroRedisCache(redisTemplateJdk, s);
        return shiroRedisCache;
    }
}
