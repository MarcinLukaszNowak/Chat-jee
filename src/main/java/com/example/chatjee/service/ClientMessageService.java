package com.example.chatjee.service;

import com.example.chatjee.domain.ClientMessage;
import com.example.chatjee.repository.ClientMessageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ClientMessageService {

    @Inject
    private ClientMessageRepository clientMessageRepository = new ClientMessageRepository();

    public void saveMessage(ClientMessage clientMessage) {
        clientMessageRepository.saveMessage(clientMessage);
    }

    public List<ClientMessage> getMessagesByRoomId(String roomId) {
        return clientMessageRepository.findMessagesByRoomId(roomId);
    }

}
