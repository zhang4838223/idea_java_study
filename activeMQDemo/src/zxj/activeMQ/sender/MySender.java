package zxj.activeMQ.sender;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by xiaojun.zhang on 2015/12/20.
 */
public class MySender {
    private static final int SEND_NUMBER=5;

    public static void main(String[] args){
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageProducer producer;

        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("firstQueue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i=1; i<=SEND_NUMBER; i++){
                TextMessage message = session.createTextMessage("ActiveMQ 消息发送" + i);
                System.out.println("ActiveMQ消息已经发出！");
                producer.send(message);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection!=null)
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
        }
    }
}
