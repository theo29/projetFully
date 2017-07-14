package com.example.TheoP.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class GAECommunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String descriptionLong;
    private String picture;
    private GAECommunity_Type community_type;
    private Date dateTimeCreated;
    private GAEUser useradmin;

    public GAECommunity(){

    }

    public GAECommunity(String name, String descriptionLong, String picture, GAECommunity_Type community_type, Date dateTimeCreated, GAEUser useradmin) {
        this.name = name;
        this.descriptionLong = descriptionLong;
        this.picture = picture;
        this.community_type = community_type;
        this.dateTimeCreated = dateTimeCreated;
        this.useradmin = useradmin;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescriptionLong() {return descriptionLong;}

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public GAECommunity_Type getCommunity_type() {
        return community_type;
    }

    public void setCommunity_type(GAECommunity_Type community_type) {
        this.community_type = community_type;
    }

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public GAEUser getUseradmin() {
        return useradmin;
    }

    public void setUseradmin(GAEUser useradmin) {
        this.useradmin = useradmin;
    }

    public long getId() {return id;}




}
