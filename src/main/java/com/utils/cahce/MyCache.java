package com.utils.cahce;


import java.util.*;

/**
 * @Author: junping.chi@luckincoffee.com
 * @Date: 2019/12/25 17:24
 * @Description:
 */
public class MyCache extends BaseGuavaCache<String, List<String>> {


    @Override
    public Map<? extends String, ? extends List<String>> loadValueWhenStarted() {
        HashMap<String, List<String>> stringListHashMap = new HashMap<>(16);
        stringListHashMap.put("rose01", Arrays.asList("rose01", "222"));
        stringListHashMap.put("rose02", Arrays.asList("rose02", "222"));
        stringListHashMap.put("rose03", Arrays.asList("rose03", "222"));
        stringListHashMap.put("rose04", Arrays.asList("rose04", "222"));
        return stringListHashMap;
    }

    @Override
    protected List<String> reloadValueWhenExpired(String key) throws Exception {
        final String jack = "jack";
        List<String> newValue = Arrays.asList();
        if (jack.equals(key)) {
            newValue = Arrays.asList("aaa", "bbb", new Random().nextInt(100000) + "——模拟从Mysql获取最新数据");
        }
        return newValue;
    }


}
