package com.java8;

import java.time.*;

/**
 * java.time
 * 新的java.time包涵盖了所有处理日期，时间，日期/时间，时区，时刻（instants），过程（during）与时钟（clock）的操作。
 * 使用时区的日期时间API
 * 如果我们需要考虑到时区，就可以使用时区的日期时间API：
 */
public class Java8_timeZone {
    public static void main(String args[]){
        Java8_timeZone java8tester = new Java8_timeZone();
        java8tester.testZonedDateTime();
    }

    public void testZonedDateTime(){

        // 获取当前时间日期
        ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
        System.out.println("date1: " + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("当期时区: " + currentZone);
    }
}