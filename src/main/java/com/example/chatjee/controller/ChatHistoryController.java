package com.example.chatjee.controller;

import com.example.chatjee.domain.ClientMessage;
import com.example.chatjee.dto.ClientMessageDto;
import com.example.chatjee.service.ClientMessageService;
import lombok.Getter;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/history")
public class ChatHistoryController {

    @Inject
    private ClientMessageService clientMessageService;

    @GET
    @Path("{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomHistory(@PathParam("roomId") String roomId) {
        List<ClientMessageDto> clientMessages = clientMessageService.getMessagesByRoomId(roomId).stream()
                .map(ClientMessage::toDto)
                .toList();
        return Response.ok(clientMessages).build();
    }

}
