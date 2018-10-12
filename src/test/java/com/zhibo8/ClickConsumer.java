//package com.zhibo8;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class ClickConsumer {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Value("${hbase.table.click}")
//    private String clickTableName;
//
//    @Autowired
//    private PhoenixMsgSender phoenixMsgSender;
//
//    /**
//     * kafka 监听器，批量消费
//     *
//     * @param records
//     * @param ack
//     */
//    @KafkaListener(topics = "${kafka.consumer.topic-click}", containerFactory = "batchFactory")
//    public void listen(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//        try {
//            //HbaseMsgSender.save2Hbase(records, ack, clickTableName, Constants.CLICK_FAMILY);
//            //phoenixMsgSender.insertBatch(records);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            ack.acknowledge();//手动提交偏移量
//        }
//    }
//}
