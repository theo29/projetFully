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
public class GAEChatCommunity extends GAEChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private GAECommunity community;

    public GAEChatCommunity(){

    }

    public GAEChatCommunity(GAECommunity community, String messageChat, Date dateTimeChat,
            GAEMember member) {
        this.community = community;
        this.messageChat=messageChat;
        this.dateTimeChat=dateTimeChat;
        this.member=member;
    }

    public GAECommunity getCommunity() {
        return community;
    }

    public void setCommunity(GAECommunity community) {
        this.community = community;
    }
}
