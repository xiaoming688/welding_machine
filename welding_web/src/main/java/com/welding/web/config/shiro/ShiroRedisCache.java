package com.welding.web.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * @author MM
 * @create 2019-01-15 14:40
 **/
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;

    private String prefix = "shiro_redis";

    public String getPrefix() {
        return prefix + ":";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ShiroRedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ShiroRedisCache(RedisTemplate redisTemplate, String prefix) {
        this(redisTemplate);
        this.prefix = prefix;
    }

    @Override
    public V get(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        System.out.println("get cache key: "+ buildCacheKey(k));
        return (V) redisTemplate.opsForValue().get(buildCacheKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (k == null || v == null) {
            return null;
        }
        System.out.println("put cache key: "+ buildCacheKey(k));
        redisTemplate.opsForValue().set(buildCacheKey(k), v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        System.out.println("remove cache key: "+ buildCacheKey(k));
        V v = (V) redisTemplate.opsForValue().get(buildCacheKey(k));
        redisTemplate.delete(buildCacheKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        System.out.println("shiro redis cache clear:   ");
//        redisTemplateJdk.getConnectionFactory().getConnection().flushDb();

    }

//    @Override
//    public int size() {
//        return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
//    }

    @Override
    public int size() {
        if (keys() == null) {
            return 0;
        }
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        byte[] bytes = (getPrefix() + "*").getBytes();
        Set<byte[]> keys = redisTemplate.keys(bytes);
        Set<K> sets = new HashSet<>();
        for (byte[] key : keys) {
            sets.add((K) key);
        }
        return sets;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            values.add(get(k));
        }
        return values;
    }

    private String buildCacheKey(Object key) {
        return getPrefix() + ":" + key;
    }


}
