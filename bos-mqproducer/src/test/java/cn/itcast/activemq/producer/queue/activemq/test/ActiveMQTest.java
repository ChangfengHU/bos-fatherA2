/*
package cn.itcast.activemq.producer.queue.activemq.test;

import java.util.HashMap;
import java.util.Map;

import cnm.QueueSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-producer.xml")
public class ActiveMQTest {
    @Autowired
    public QueueSender queueSender;

    @Test
    public void testMQ() {
      */
/*  for (int i = 0; i < 10; i++) {
            queueSender.send("bos_sms", "Hello,Queue");
        }*//*

        // topicSender.send("test.topic", "Hello,Topic");
        Map<String, String> map = new HashMap<>();
        map.put("telephone", "15001932131");
        map.put("code", "5656");
        queueSender.send("bos_sms", map);
    }
}*/
