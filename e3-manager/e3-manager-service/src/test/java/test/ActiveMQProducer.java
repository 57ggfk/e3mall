package test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * Created by wangji on 2017/1/20.
 */
public class ActiveMQProducer {
    @Test
    public void testQueueProducer() throws Exception {
        // 第一步：创建ConnectionFactory
        String brokerURL = "tcp://192.168.116.138:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        // 第二步：通过工厂，创建Connection
        Connection connection = connectionFactory.createConnection();
        // 第三步：连接启动
        connection.start();
        // 第四步：通过连接获取session会话
        // 第一个参数：是否启用ActiveMQ的事务，如果为true，第二个参数失效。
        // 第二个参数：应答模式,AUTO_ACKNOWLEDGE为自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：通过session创建destination，两种方式：Queue、Topic
        // 参数：消息队列的名称，在后台管理系统中可以看到
        Queue queue = session.createQueue("test-queue");
        // 第六步：通过session创建MessageProducer
        MessageProducer producer = session.createProducer(queue);
        // 第七步：创建Message
        // 方式1：
        TextMessage message = new ActiveMQTextMessage();
        message.setText("queue test");
        // 方式2：
        // TextMessage message = session.createTextMessage("queue test");
        // 第八步：通过producer发送消息
        producer.send(message);
        // 第九步：关闭资源
        producer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testTopicProducer() throws Exception {
        // 第一步：创建ConnectionFactory
        String brokerURL = "tcp://192.168.116.138:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        // 第二步：通过工厂，创建Connection
        Connection connection = connectionFactory.createConnection();
        // 第三步：连接启动
        connection.start();
        // 第四步：通过连接获取session会话
        // 第一个参数：是否启用ActiveMQ的事务，如果为true，第二个参数失效。
        // 第二个参数：应答模式,AUTO_ACKNOWLEDGE为自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：通过session创建destination，两种方式：Queue、Topic
        // 参数：主题的名称，在后台管理系统中可以看到
        Topic topic = session.createTopic("test-topic");
        // 第六步：通过session创建MessageProducer
        MessageProducer producer = session.createProducer(topic);
        // 第七步：创建Message
        // 方式1：
        TextMessage message = new ActiveMQTextMessage();
        message.setText("topic test");
        // 方式2：
        // TextMessage message = session.createTextMessage("queue test");
        // 第八步：通过producer发送消息
        producer.send(message);
        // 第九步：关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testSpringQueueProducer() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        javax.jms.Destination dest = (Destination) ctx.getBean("queueDestination");
        JmsTemplate jms = ctx.getBean(JmsTemplate.class);
        jms.send( dest, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage("Hello Spring Quee Producer send to you");
            }
        });
    }

}
