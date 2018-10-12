//package com.zhibo8;
//
//import com.alibaba.fastjson.JSON;
//import com.zhibo8.warehouse.commons.utils.PhoenixJDBCUtil;
//import com.zhibo8.warehouse.pojo.ClickLog;
//import org.apache.commons.lang.StringUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class PhoenixMsgSender {
//    private int upsertBatchSize = 1000;
//    private String tableName = "user_clicklog";
//
//    public void insertBatch(List<ConsumerRecord<?, ?>> records) throws Exception {
//        List<Object> dataList = new ArrayList<>();
//        for (ConsumerRecord<?, ?> record : records) {
//            ClickLog clickLog = JSON.parseObject(record.value().toString(), ClickLog.class);
//            //过滤
//            if (StringUtils.isBlank(clickLog.getTime())) continue;
//            //放入集合
//            dataList.add(clickLog);
//        }
//        //final String sql = "upsert into user_clicklog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        PhoenixJDBCUtil.insertBatch2(tableName, dataList, upsertBatchSize);
//    }
//}
