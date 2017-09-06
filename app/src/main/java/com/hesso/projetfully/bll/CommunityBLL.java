package com.hesso.projetfully.bll;

import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.hesso.projetfully.GAE.EndpointsAsyncTaskCommunity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CommunityBLL {
    public static long currentCommunity_id = 0;

    public static List<GAECommunity> getAll_Community() {
        List<GAECommunity> gaeCommunities = new ArrayList<GAECommunity>();

        try {
            gaeCommunities = new EndpointsAsyncTaskCommunity().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (gaeCommunities == null) {
            gaeCommunities = new ArrayList<GAECommunity>();
        }

        return gaeCommunities;
    }

    public static GAECommunity getCommunityById(long community_id) {
        GAECommunity gaeCommunity = new GAECommunity();
        try {
            gaeCommunity = new EndpointsAsyncTaskCommunity(PFG_Fulltopia.QUERY_SELECT, community_id).execute().get().get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (gaeCommunity == null)
            gaeCommunity = new GAECommunity();
        return gaeCommunity;
    }
}
