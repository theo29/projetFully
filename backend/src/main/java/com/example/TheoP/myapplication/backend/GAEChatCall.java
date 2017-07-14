package com.example.TheoP.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Axel on 14.07.2017.
 */

@Entity
public class GAEChatCall extends GAEChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private GAECall call;

    public GAEChatCall() {

    }

    public GAEChatCall(GAECall call, String messageChat, Date dateTimeChat,
                       GAEMember member) {
        this.call = call;
        this.messageChat = messageChat;
        this.dateTimeChat = dateTimeChat;
        this.member = member;
    }

    public GAECall getCall() {
        return call;
    }

    public void setCall(GAECall call) {
        this.call = call;
    }
}
