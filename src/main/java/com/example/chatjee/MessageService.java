package com.example.chatjee;

import lombok.SneakyThrows;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Messages")
})
public class MessageService implements MessageListener {

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        var text = message.getBody(String.class);
        System.out.println(text);

//        var proxyFactory = new ProxyFactory();
//        ConnectionFactory connectionFactory = proxyFactory.createProxy("jms/topic/Messages");
//        JMSContext jmsContext = connectionFactory.createContext();
//        jmsContext.createProducer().send(message.getJMSReplyTo(), "server: otrzymałem wiadomość");

    }


}
