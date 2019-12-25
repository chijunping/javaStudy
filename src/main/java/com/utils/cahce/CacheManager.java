package com.utils.cahce;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: junping.chi@luckincoffee.com
 * @Date: 2019/12/25 18:19
 * @Description:
 */
public class CacheManager {
    public static MyCache myCache = new MyCache();

    static {
        myCache.setRefreshDuration(10)
                .setRefreshTimeUnit(TimeUnit.SECONDS)
                .setMaxSize(10);
    }

    /**
     * 测试Mycache
     *
     * @throws InterruptedException
     */
    @Test
    public void testCache() throws InterruptedException {
        List<String> jack0 = myCache.getValueOrDefault("jack", Arrays.asList("1", "2"));
        System.out.println("首次获取：" + JSON.toJSON(jack0));
        List<String> jack01 = myCache.getValueOrDefault("jack01", Arrays.asList("jack01", "2"));
        List<String> jack02 = myCache.getValueOrDefault("jack02", Arrays.asList("jack02", "2"));
        List<String> jack03 = myCache.getValueOrDefault("jack03", Arrays.asList("jack03", "2"));
        Thread.sleep(8000);
        List<String> jack_before_expire = myCache.getValueOrDefault("jack", Arrays.asList("1", "2"));
        System.out.println("过期之前：" + JSON.toJSON(jack_before_expire));
        Thread.sleep(4000);
        List<String> jack_after_expire = myCache.getValueOrDefault("jack", Arrays.asList("1", "2"));
        System.out.println("过期之后：" + JSON.toJSON(jack_after_expire));
    }
}
