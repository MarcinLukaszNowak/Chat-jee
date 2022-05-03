import org.jgroups.Message;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private static final String MESSAGES_TOPIC_JNDI_NAME = "jms/topic/Messages";
    private static final String CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";

    public static void main(String[] args) throws Exception {
        var proxyFactory = new ProxyFactory();
        ConnectionFactory connectionFactory = proxyFactory.createProxy(CONNECTION_FACTORY_JNDI_NAME);
        Topic topic = proxyFactory.createProxy(MESSAGES_TOPIC_JNDI_NAME);

        new Thread(new MessageSender(connectionFactory, topic)).run();


        try (JMSContext jmsContext = connectionFactory.createContext()) {
            JMSConsumer consumer = jmsContext.createConsumer(topic);
//            System.out.println(consumer.receive());
            while (true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    jmsContext.createProducer().setJMSCorrelationID("xd").send(topic, scanner.nextLine());
                }
            }

        }
    }

}
