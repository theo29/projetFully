package com.example.TheoP.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Axel on 14.07.2017.
 */


@Entity
public class GAECommunity_Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;

    public GAECommunity_Type() {

    }


    public GAECommunity_Type(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
