import lombok.AllArgsConstructor;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
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
                jmsContext.createProducer().send(topic, scanner.nextLine());
            }
        }
    }

}
