import lombok.AllArgsConstructor;

import javax.jms.*;
import java.util.Scanner;

@AllArgsConstructor
public class MessageSender implements Runnable {

    ConnectionFactory connectionFactory;
    Topic topic;

    @Override
    public void run() {
        JMSContext jmsContext = connectionFactory.createContext();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                TextMessage textMessage = jmsContext.createTextMessage(scanner.nextLine());
                try {
                    textMessage.setJMSCorrelationID("xd");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                jmsContext.createProducer().send(topic, textMessage);
            }
        }
    }

}
