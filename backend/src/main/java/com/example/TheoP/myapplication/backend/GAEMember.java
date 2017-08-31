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
public class GAEMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private GAEUser user;
    private GAECommunity community;


    public GAEMember() {

    }

    public GAEMember(GAEUser user, GAECommunity community) {
        this.user = user;
        this.community = community;
    }

    public GAEUser getUser() {
        return user;
    }

    public void setUser(GAEUser user) {
        this.user = user;
    }

    public GAECommunity getCommunity() {
        return community;
    }

    public void setCommunity(GAECommunity community) {
        this.community = community;
    }
}
