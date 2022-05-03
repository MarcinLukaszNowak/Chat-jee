package message;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.jms.*;

@AllArgsConstructor
@Slf4j
public class MessageSender {

    private JMSContext jmsContext;
    private Topic topic;

    public void sendMessage(ClientMessage clientMessage) {
        try {
            TextMessage textMessage = jmsContext.createTextMessage(clientMessage.createMessageText());
            textMessage.setJMSCorrelationID(clientMessage.getRoomId());
            jmsContext.createProducer().send(topic, textMessage);
        } catch (JMSException e) {
            log.error("Message not send.");
            e.printStackTrace();
        }
    }

}
