package com.welding.web.config.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author MM
 * @create 2019-01-15 14:38
 **/
public class ShiroRedisCacheManager extends AbstractCacheManager {

    private RedisTemplate redisTemplate;

    public ShiroRedisCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Cache createCache(String s) throws CacheException {
        return new ShiroRedisCache(redisTemplate, s);
    }
}
