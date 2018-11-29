package com.JUC;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 实际可参考代码
 * @Description:
 * @Date: Created in 9:28 2018/10/19
 * @Modified By:
 */
public class Zb8_JUCTest {
    /**
     * 多线程进行“启动APP”查询
     *
     * @param records
     * @throws Exception
     */
    public void insertBatch2(List<ConsumerRecord<?, ?>> records) throws Exception {
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//        List<ClickLog> startAappList = new ArrayList<>();
//        List<Object> dataList = new ArrayList<>();
//        for (ConsumerRecord<?, ?> record : records) {
//            ClickLog clickLog = record2Bean(JSON.parseObject(record.value().toString(), Map.class));
//            //过滤
//            if (clickLog == null) continue;
//            if (StringUtils.isBlank(clickLog.getId())) continue;
//            //统一url格式并从redis关联内容的标签
//            if (StringUtils.isNotBlank(clickLog.getUrl())) {
//                clickLog = getContentLabelFromRedis(clickLog);
//                //放入集合
//                dataList.add(clickLog);
//            }
//            //更新上次启动APP的总用时
//            if ("启动APP".equalsIgnoreCase(clickLog.getEvent())) {
//                startAappList.add(clickLog);
//            }
//        }
//        //多线程查询
//        for (int i = 0; i < startAappList.size(); i++) {
//            final int j = i;
//            Runnable run = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        ClickLog clickLog = proccessStartRecord(startAappList.get(j));
//                        if (clickLog != null) {
//                            dataList.add(clickLog);
//                        }
//                    } catch (Exception e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            };
//            threadPool.execute(run);
//        }
//        threadPool.shutdown();
//        while (true) {
//            boolean isShuntDown = threadPool.awaitTermination(1, TimeUnit.SECONDS);
//            if (isShuntDown) break;
//        }
//        //插入
//        PhoenixJDBCUtil.insertBatch2(tableName, dataList, upsertBatchSize);
    }
    }
