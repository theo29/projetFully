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
    private Long id;
    private String name;
    private String descriptionLong;
    private long idCommunity_type;
    private String idUserAdmin;

    public GAECommunity() {

    }

    public GAECommunity(String name, String descriptionLong, long idCommunity_type, String idUserAdmin) {
        this.name = name;
        this.descriptionLong = descriptionLong;
        this.idCommunity_type = idCommunity_type;
        this.idUserAdmin = idUserAdmin;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdCommunity_type() {
        return idCommunity_type;
    }

    public void setIdCommunity_type(long idCommunity_type) {
        this.idCommunity_type = idCommunity_type;
    }

    public String getIdUserAdmin() {
        return idUserAdmin;
    }

    public void setIdUserAdmin(String idUserAdmin) {
        this.idUserAdmin = idUserAdmin;
    }

    public Long getId() {
        return id;
    }


}
