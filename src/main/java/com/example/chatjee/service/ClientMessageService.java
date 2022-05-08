package com.example.chatjee.service;

import com.example.chatjee.domain.ClientMessage;

import java.util.List;

public interface ClientMessageService {

    void saveMessage(ClientMessage clientMessage);

    List<ClientMessage> getMessagesByRoomId(String roomId);

}
