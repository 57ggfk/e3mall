package test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.*;

/**
 * Created by wangji on 2017/1/20.
 */
public class ActiveMQConsumer {
    @Test
    public void testQueueConsumer() throws Exception {
        // 第一步：创建ConnectionFactory
        String brokerURL = "tcp://192.168.116.138:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        // 第二步：通过工厂，创建Connection
        Connection connection = connectionFactory.createConnection();
        // 第三步：打开连接
        connection.start();
        // 第四步：通过Connection创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：通过session创建Consumer
        Queue queue = session.createQueue("test-queue");
        MessageConsumer consumer = session.createConsumer(queue);
        // 第六步：通过consumer接收信息（两种方式：一种是receive方法接收，一种通过监听器接收）
        // 第七步：通过监听器接收信息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 第七步：处理信息
                if (message instanceof TextMessage) {
                    TextMessage tm = (TextMessage) message;
                    try {
                        String text = tm.getText();
                        System.err.println(text);
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        // 等待输入
        System.in.read();

        // 第八步：关闭资源
        consumer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testTopicConsumer() throws Exception {
        // 第一步：创建ConnectionFactory
        String brokerURL = "tcp://192.168.116.138:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        // 第二步：通过工厂，创建Connection
        Connection connection = connectionFactory.createConnection();
        // 第三步：打开连接
        connection.start();
        // 第四步：通过Connection创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：通过session创建Consumer
        Topic topic = session.createTopic("test-topic");
        MessageConsumer consumer = session.createConsumer(topic);
        // 第六步：通过consumer接收信息:通过监听器接收
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 第七步：处理信息
                if (message instanceof TextMessage) {
                    TextMessage tm = (TextMessage) message;
                    try {
                        String text = tm.getText();
                        System.out.println(text);
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        // 第八步：关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testSpringCoinsumer() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        System.out.println("topic consumer 1:");
        System.in.read();
    }


}
