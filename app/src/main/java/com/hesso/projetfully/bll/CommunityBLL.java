package com.hesso.projetfully.bll;

import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.example.theop.myapplication.backend.gAECommunityTypeApi.model.GAECommunityType;
import com.example.theop.myapplication.backend.gAEMemberApi.model.GAEMember;
import com.hesso.projetfully.GAE.EndpointsAsyncTaskCommunity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.hesso.projetfully.bll.PFG_Fulltopia.getAll_CommunityTypes;

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

    public static boolean isValidCommunity(GAECommunity gaeCommunity) {
        if (gaeCommunity.getName().isEmpty()) return false;
        return !gaeCommunity.getDescriptionLong().isEmpty();

    }

    public static GAECommunity editCommunity(GAECommunity gaeCommunity) {
        GAECommunity editedCommunity = new GAECommunity();

        try {
            if (gaeCommunity.getId() > 0) {
                editedCommunity = new EndpointsAsyncTaskCommunity(gaeCommunity).execute().get().get(0);
            } else {
                new EndpointsAsyncTaskCommunity(gaeCommunity).execute().get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (editedCommunity == null)
            editedCommunity = new GAECommunity();

        return editedCommunity;

    }

    public static boolean remove(Long community_id) {
        try {
            new EndpointsAsyncTaskCommunity(PFG_Fulltopia.QUERY_REMOVE, community_id).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean getIamAdmin(GAECommunity community) {
        if (community == null) return false;
        return community.getIdUserAdmin() == PFG_Fulltopia.getCurrentUserID();

    }

    public static boolean getIamMember(GAECommunity community) {
        GAEMember gaeMember = MemberBLL.getMemberByUser_Community(PFG_Fulltopia.getCurrentUserID(), community.getId());
        if (gaeMember == null) return false;
        if (gaeMember.isEmpty()) return false;
        return (gaeMember.getUserId().equals(PFG_Fulltopia.getCurrentUserID())
                && gaeMember.getCommunityId().equals(community.getId()));

    }

    public static boolean joinCommunity(GAECommunity community) {
        if (community == null) return false;
        if (getIamMember(community)) return true;   // already member
        GAEMember gaeMember = new GAEMember();
        gaeMember.setId((long) 0);
        gaeMember.setUserId(PFG_Fulltopia.getCurrentUserID());
        gaeMember.setCommunityId(community.getId());
        MemberBLL.editMember(gaeMember);
        return true;
    }

    public static boolean leaveCommunity(GAECommunity community) {
        if (community == null) return true;
        if (!getIamMember(community)) return true;   // never member
        GAEMember gaeMember = MemberBLL.getMemberByUser_Community(PFG_Fulltopia.getCurrentUserID(), community.getId());
        if (gaeMember == null) return true;
        MemberBLL.remove(gaeMember.getId());
        return true;
    }



}
