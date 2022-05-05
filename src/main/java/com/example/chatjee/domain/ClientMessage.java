package com.example.chatjee.domain;

import com.example.chatjee.dto.ClientMessageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
//@Table
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String roomId;

    @Column
    private String userName;

    @Column
    private String text;

    @Column
    private Date sendDate;

    public ClientMessage(String roomId, String userName, String text, Date sendDate) {
        this.roomId = roomId;
        this.userName = userName;
        this.text = text;
        this.sendDate = sendDate;
    }

    public ClientMessageDto toDto() {
        ClientMessageDto clientMessageDto =  new ClientMessageDto();
        clientMessageDto.setRoomId(this.roomId);
        clientMessageDto.setUserName(this.userName);
        clientMessageDto.setText(this.text);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.sendDate);
        clientMessageDto.setSendDate(date);
        return clientMessageDto;
    }

}
