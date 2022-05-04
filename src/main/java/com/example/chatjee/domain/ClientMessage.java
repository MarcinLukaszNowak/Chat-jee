package com.example.chatjee.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMessage {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String roomId;

    @Column
    private String userName;

    @Column
    private String text;

    @Column // todo string zamienić na date
    private String sendDate;

    public ClientMessage(String roomId, String userName, String text, String sendDate) {
        this.roomId = roomId;
        this.userName = userName;
        this.text = text;
        this.sendDate = sendDate;
    }



}
