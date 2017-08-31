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
    private Long idMemberCreator;
    private String description;
    private Date dateend;
    private String mapposition;

    public GAECall() {

    }

    public GAECall(Long idMemberCreator, String description, Date dateend, String mapposition) {
        this.idMemberCreator = idMemberCreator;
        this.description = description;
        this.dateend = dateend;
        this.mapposition = mapposition;
    }

    @Override
    public String toString() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Long getidMemberCreator() {
        return idMemberCreator;
    }

    public void setidMemberCreator(Long idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    public String getMapposition() {
        return mapposition;
    }

    public void setMapposition(String mapposition) {
        this.mapposition = mapposition;
    }

}
