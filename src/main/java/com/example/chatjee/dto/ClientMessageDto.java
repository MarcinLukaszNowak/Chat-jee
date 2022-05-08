package com.example.chatjee.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientMessageDto implements Serializable {

    private String roomId;
    private String userName;
    private String text;
    private String sendDate;

}
