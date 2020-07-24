package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    private static LoadingCache<String,String> localcache = CacheBuilder.newBuilder().initialCapacity(1000)
   .maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
   .build(new CacheLoader<String, String>() {
       //默认的数据加载实现，当get方法获取不到key值时默认调用
        @Override
         public String load(String s) throws Exception {
             return null;
         }
   });

    public static void setKey(String key,String value){
        localcache.put(key, value);
    }

    public static String getKey(String key){
        String value = "";
        try{
            value = localcache.get(key);
            if ("".equals(value)){
                return null;
            }
        }catch (Exception e){
            logger.error("get logger error");
        }
        return value;
    }

}
