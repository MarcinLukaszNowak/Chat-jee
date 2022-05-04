package com.example.chatjee.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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



}
