package com.JUC;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 子线程并发执行不同任务并返回各自的结果，最后主线程整合子线程组的结果
 */
public class ExecutorsTest {

    public static void main(String[] args) throws Exception {
        long intTime = System.currentTimeMillis();
        String sql01 = "select name...";
        String sql02 = "select age...";
        String sql03 = "select detail...";
        ExecutorService threadPool = Executors.newCachedThreadPool();
        //并行执行不同任务
        Future<String> future01 = threadPool.submit(new taskName(sql01));
        Future<Integer> future02 = threadPool.submit(new taskAge(sql02));
        Future<Map<String, Object>> future03 = threadPool.submit(new taskDetail(sql03));
        String name = future01.get();
        Integer age = future02.get();
        Map<String, Object> detail = future03.get();

        threadPool.shutdown();
        long startTime = System.currentTimeMillis();
        while (!threadPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("service not stop");
        }
        System.out.println("all thread complete");

        long endTime = System.currentTimeMillis();
        System.out.println("并行时间=" + (endTime - startTime));
        //根据并行结果进行进一步逻辑
        String msg = "My name is " + name + ", i'm " + age + " years old,my detail message is " + detail.toString();
        System.out.println(msg);

        long totalTime = System.currentTimeMillis();
        System.out.println("total时间=" + (totalTime - intTime));

    }


}

class taskName implements Callable<String> {
    private String sql;

    public taskName(String sql01) {
        this.sql = sql01;
    }

    @Override
    public String call() throws Exception {
        //执行sql01查询
        long startTime = System.currentTimeMillis();

        Thread.sleep(10000);

        long endTime = System.currentTimeMillis();
        System.out.println(sql + ",startTime=" + startTime + ",endTime=" + endTime);
        return "jack";
    }
}

class taskAge implements Callable<Integer> {
    private String sql;

    public taskAge(String sql02) {
        this.sql = sql02;
    }

    @Override
    public Integer call() throws Exception {
        //执行sql01查询
        long startTime = System.currentTimeMillis();

        Thread.sleep(4000);

        long endTime = System.currentTimeMillis();
        System.out.println(sql + ",startTime=" + startTime + ",endTime=" + endTime);
        return 26;
    }
}

class taskDetail implements Callable<Map<String, Object>> {
    private String sql;

    public taskDetail(String sql03) {
        this.sql = sql03;
    }

    @Override
    public Map<String, Object> call() throws Exception {
        //执行sql01查询
        long startTime = System.currentTimeMillis();

        Thread.sleep(4000);
        Map<String, Object> detailMap = new HashMap<>();
        detailMap.put("phone", "15320398576");
        detailMap.put("addrr", "xiamen Fujian");
        long endTime = System.currentTimeMillis();
        System.out.println(sql + ",startTime=" + startTime + ",endTime=" + endTime);
        return detailMap;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(6);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            //方式1
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(4000);
                        strings.add(Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getName() + " started ,time=" + System.currentTimeMillis());
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }

                }
            };
            service.execute(run);
            //方式2
//            Callable<String> callable = new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    Thread.sleep(4000);
//                    System.out.println(Thread.currentThread().getName() + " started ,time=" + System.currentTimeMillis());
//                    return Thread.currentThread().getName();
//                }
//            };
//
//            Future<String> submit = service.submit(callable);
//            strings.add(submit.get());
//        }

            service.shutdown();
            while (!service.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("service not stop");
            }
            for (String string : strings) {
                System.out.println(string);
            }
            System.out.println("all thread complete");
        }


    }

}

