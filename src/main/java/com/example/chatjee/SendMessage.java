package com.example.chatjee;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class SendMessage {

    public void go() throws Exception {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("jboss.naming.client.ejb.contex", "true");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        Context ctx = new InitialContext(jndiProperties);
        Topic topic = (Topic) ctx.lookup("jms/topic/Messages"); // wyciągniete z wildfly localhost:9990
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory"); // wyciągniete z wildfly localhost:9990
        Connection connection = cf.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(topic);

        TextMessage message = session.createTextMessage("hello");

        messageProducer.send(message);
        connection.close();

    }

}
