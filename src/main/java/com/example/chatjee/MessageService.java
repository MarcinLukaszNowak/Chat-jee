package com.example.chatjee;

import com.example.chatjee.domain.ClientMessage;
import com.example.chatjee.service.ClientMessageService;
import lombok.SneakyThrows;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Messages")
})
public class MessageService implements MessageListener {

    @Inject
    private ClientMessageService clientMessageService = new ClientMessageService();

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        var text = message.getBody(String.class);
        System.out.println(text);
        clientMessageService.saveMessage(new ClientMessage("xd", "un1", "text", "date"));

    }




}
