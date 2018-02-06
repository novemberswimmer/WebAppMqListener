package com.ruel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class JmsMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageListener.class);

    @Autowired
    private JmsTemplate template = null;

    @Autowired
    private JmsMessageProducer jmsMessageProducer;

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage)message;
                String msg = tm.getText();


                logger.info("Processed message '{}'", msg);

                jmsMessageProducer.generateMessages(msg, message.getJMSMessageID());
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    }

}