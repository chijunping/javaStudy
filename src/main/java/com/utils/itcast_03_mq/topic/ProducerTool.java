package com.utils.itcast_03_mq.topic;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
     
public class ProducerTool {        
    private String user = ActiveMQConnection.DEFAULT_USER;         
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;       
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;       
    private String subject = "mytopic";      
    private Destination destination = null;      
    private Connection connection = null;      
    private Session session = null;      
    private MessageProducer producer = null;
    // 初始化      
    private void initialize() throws JMSException, Exception {      
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);//链接工厂      
        connection = connectionFactory.createConnection();//创建链接
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//创建jms.session      
        destination = session.createTopic(subject);//创建destination（topic）   
        producer = session.createProducer(destination);//创建生产者    
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//设置数据传输模式      
    }
    // 发送消息 
    public void produceMessage(String message) throws Exception {      
        initialize();      
        TextMessage msg = session.createTextMessage(message);      
        connection.start();      
        System.out.println("Producer:->Sending message: " + message);      
        producer.send(msg);      
        System.out.println("Producer:->Message sent complete!");      
    }
    // 关闭连接      
    public void close() throws JMSException {      
        System.out.println("Producer:->Closing connection");      
        if (producer != null)      
            producer.close();      
        if (session != null)      
            session.close();      
        if (connection != null)      
            connection.close();      
    }      
}        