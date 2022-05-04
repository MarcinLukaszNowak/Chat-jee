package com.example.chatjee.jms;

import com.example.chatjee.domain.ClientMessage;
import com.example.chatjee.service.ClientMessageService;
import lombok.SneakyThrows;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Messages")
})
public class ClientMessageListener implements MessageListener {

    @Inject
    private ClientMessageService clientMessageService = new ClientMessageService();

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        var text = message.getBody(String.class);
        String[] textElements = text.split(": ", 2);
        ClientMessage clientMessage = new ClientMessage(message.getJMSCorrelationID(), textElements[0], textElements[1], new Date());
        clientMessageService.saveMessage(clientMessage);
    }

}
