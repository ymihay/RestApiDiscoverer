package com.smartbear.alertsite.web.rest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;
import com.smartbear.alertsite.service.DiscoverResourcesService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yanamikhaylenko on 5/19/15.
 */
public abstract class SwaggerResourcesController {
    //MapMaker map = new MapMaker().weakValues().expiration(5, TimeUnit.MINUTES).makeMap();
    LoadingCache<String, DiscoverResourcesService> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, DiscoverResourcesService>() {
                public DiscoverResourcesService load(String key)  {
                    return new DiscoverResourcesService(key);
                }
            });
}
