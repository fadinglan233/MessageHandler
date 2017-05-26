package handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import mysql.DeviceInfo;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import protocol.SocketDefaultMsg;

import javax.jms.*;
import java.util.Map;

/**
 * Created by fadinglan on 2017/5/25.
 */
public class MsgConsumer {

    private final static Log logger = LogFactory.getLog(MsgConsumer.class);
    public static void main(String[] args){

        /**
         * JMS 连接生产工厂
         */
        ConnectionFactory connectionFactory;

        /**
         * JMS客户端 到 Provider的连接
         */
        Connection connection = null;

        /**
         * 发送或者接受的线程
         */
        Session session;

        /**
         * 消息的目的地
         */
        Destination destination;

        /**
         * 消息消费者
         */
        MessageConsumer consumer;

        connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://139.224.54.233:61616/");
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("firstQueue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        String textMessage = ((TextMessage) message).getText();
                        final SocketDefaultMsg msg = JSON.parseObject(textMessage, SocketDefaultMsg.class);
                        if (null != msg){
                            switch (msg.getMsgType()){
                                case 2:
                                    String data = getData(msg.getParams());
                                    logger.debug("sleep data is : [" + data + "]");
                                    DeviceInfo.updateDevice(msg.getFrom(),data);
                                    break;
                                case 3:
                                    DeviceInfo.deleteDevice(msg.getFrom());
                            }

                        }
                    }catch (Exception e){
                       logger.error("message :[" + message + "] can't be applied");
                    }
                }
            });

        }catch (Exception e){

        }
    }

    private static String getData(JSONArray params){
        Object object = params.get(0);
        Map<String, JSONArray> sleepMap = (Map<String,JSONArray>) object;

        Object[] ob = sleepMap.get("rate").toArray();
        String data = "";
        for (Object o:ob) {
            data = data + o + " ";
        }
        return data;

    }
}
