package com.example.TheoP.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class GAECall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idMemberCreator;
    private String description;
    private String dateend;
    private String lieu;

    public GAECall() {

    }

    public GAECall(String idMemberCreator, String description, String dateend, String lieu) {
        this.idMemberCreator = idMemberCreator;
        this.description = description;
        this.dateend = dateend;
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getidMemberCreator() {
        return idMemberCreator;
    }

    public void setidMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

}
