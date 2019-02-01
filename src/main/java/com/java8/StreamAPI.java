package com.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @ Author: chizi
 * @ Description:
 * @ Created Time: 2019/2/1 15:56
 * @ Modified By:
 */
public class StreamAPI {
    @Test
    public void sorted() {
        List<ClickLog> clickLogs = new ArrayList<>();
        ClickLog log1 = new ClickLog();
        log1.setId("01");
        log1.setTime("01");
        log1.setModel("01");
        log1.setPlatform("android");
        ClickLog log11 = new ClickLog();
        log11.setId("01");
        log11.setTime("011");
        log11.setModel("011");
        log11.setPlatform("android");
        log1.setPlatform("android");
        ClickLog log111 = new ClickLog();
        log111.setId("01");
        log111.setTime("0111");
        log111.setModel("0111");
        log111.setPlatform("android");
        ClickLog log2 = new ClickLog();
        log2.setId("02");
        log2.setTime("02");
        log2.setModel("02");
        log2.setPlatform("android");
        ClickLog log3 = new ClickLog();
        log3.setId("03");
        log3.setTime("03");
        log3.setModel("03");
        log3.setPlatform("android");
        clickLogs.add(log1);
        clickLogs.add(log11);
        clickLogs.add(log111);
        clickLogs.add(log2);
        clickLogs.add(log3);
        clickLogs.stream()
                .filter(o -> o != null && o.getPlatform() != null && o.getPlatform().equalsIgnoreCase("android"))
                .sorted(Comparator.comparing(ClickLog::getId)//第1排序字段：id,升序
                        .thenComparing(Comparator.comparing(ClickLog::getTime).reversed())//第2排序字段：time,降序
                        .thenComparing(ClickLog::getModel)//第3排序字段：model,升序
                        .thenComparing(ClickLog::getEvent)//第4排序字段：event,升序
                )
                .forEach(System.out::println);
    }
}
