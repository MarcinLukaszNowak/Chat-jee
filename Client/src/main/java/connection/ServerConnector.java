package connection;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.NamingException;

@Slf4j
public class ServerConnector {

    private static final String MESSAGES_TOPIC_JNDI_NAME = "jms/topic/Messages";
    private static final String CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";

    @Getter
    private ConnectionFactory connectionFactory;
    @Getter
    private JMSContext jmsContext;
    @Getter
    private Topic topic;

    public boolean connectWithServer() {
        try {
            ProxyFactory proxyFactory = new ProxyFactory();
            this.connectionFactory = proxyFactory.createProxy(CONNECTION_FACTORY_JNDI_NAME);
            this.jmsContext = connectionFactory.createContext();
            this.topic = proxyFactory.createProxy(MESSAGES_TOPIC_JNDI_NAME);
            log.info("Connected with the server.");
            return true;
        } catch (NamingException e) {
            log.error("Connection with the server failed.");
            e.printStackTrace();
            return false;
        }
    }

}
