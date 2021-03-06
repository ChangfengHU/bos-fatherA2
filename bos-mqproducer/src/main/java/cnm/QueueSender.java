package cnm;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 
 * @description 队列消息生产者，发送消息到队列
 * 
 */
@Service("queueSender")
public class QueueSender {

        @Autowired
        @Qualifier("jmsQueueTemplate")
        private JmsTemplate jmsTemplate;// 通过@Qualifier修饰符来注入对应的bean

        /**
         * 发送一条消息到指定的队列（目标）
         * 
         * @param queueName
         *            队列名称
         * @param message
         *            消息内容
         */
        public void send(String queueName, final String message) {
                jmsTemplate.send(queueName, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                                return session.createTextMessage(message);
                        }
                });
        }
   //  发送map结构数据
        public void send(String queueName, final Map<String, String> map) {
                jmsTemplate.send(queueName, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                                MapMessage message = session.createMapMessage();
                                for (String key : map.keySet()) {
                                        message.setString(key, map.get(key));
                                }
                                return message;
                        }
                });
        }

}