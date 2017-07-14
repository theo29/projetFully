package com.example.TheoP.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public abstract class GAEChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String messageChat;
    protected Date dateTimeChat;
    protected GAEMember member;


    public String getMessageChat() {
        return messageChat;
    }

    public void setMessageChat(String messageChat) {
        this.messageChat = messageChat;
    }

    public Date getDateTimeChat() {
        return dateTimeChat;
    }

    public void setDateTimeChat(Date dateTimeChat) {
        this.dateTimeChat = dateTimeChat;
    }

    public GAEMember getMember() {
        return member;
    }

    public void setMember(GAEMember member) {
        this.member = member;
    }
}
