package com.hesso.projetfully.bll;

import com.example.theop.myapplication.backend.gAEMemberApi.model.GAEMember;
import com.hesso.projetfully.GAE.EndpointsAsyncTaskMember;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MemberBLL {
    public static long currentMember_id = 0;

    public static List<GAEMember> getAll_Members() {
        List<GAEMember> gaeMembers = new ArrayList<GAEMember>();

        try {
            gaeMembers = new EndpointsAsyncTaskMember().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (gaeMembers == null) {
            gaeMembers = new ArrayList<GAEMember>();
        }

        return gaeMembers;
    }

    // list of Member's object for a community
    public static List<GAEMember> getAll_MembersOfCommunity(long community_id) {
        List<GAEMember> gaeMembers = new ArrayList<GAEMember>();
        List<GAEMember> allMembers = getAll_Members();
        for (GAEMember gaeM : allMembers) {
            if (gaeM.getCommunityId() == community_id) {
                gaeMembers.add(gaeM);
            }
        }

        if (gaeMembers == null) gaeMembers = new ArrayList<GAEMember>();
        return gaeMembers;
    }

    // list of Member's object for a user
    public static List<GAEMember> getAll_MembersOfUser(String user_id) {
        List<GAEMember> gaeMembers = new ArrayList<GAEMember>();
        List<GAEMember> allMembers = getAll_Members();
        for (GAEMember gaeM : allMembers) {
            if (gaeM.getUserId().equals(user_id)) {
                gaeMembers.add(gaeM);
            }
        }

        if (gaeMembers == null) gaeMembers = new ArrayList<GAEMember>();
        return gaeMembers;
    }

    public static GAEMember getMemberById(long member_id) {
        GAEMember gaeMember = new GAEMember();
        try {
            gaeMember = new EndpointsAsyncTaskMember(PFG_Fulltopia.QUERY_SELECT, member_id).execute().get().get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (gaeMember == null)
            gaeMember = new GAEMember();
        return gaeMember;
    }

    public static GAEMember getMemberByUser_Community(String user_id, long community_id) {
        GAEMember gaeMember = new GAEMember();
        List<GAEMember> gaeMembers = getAll_Members();
        if (gaeMembers != null) {
            for (GAEMember gaeM : gaeMembers) {
                if (gaeM.getUserId().equals(user_id) && gaeM.getCommunityId().equals(community_id)) {
                    return gaeM;
                }
            }
        }
        if (gaeMember == null)
            gaeMember = new GAEMember();
        return gaeMember;
    }

    public static boolean isValidMember(GAEMember gaeMember) {
        if (gaeMember == null) return false;
        if (gaeMember.getUserId() == null) return false;
        if (gaeMember.getUserId().isEmpty()) return false;
        if (gaeMember.getCommunityId() == null) return false;
        return gaeMember.getCommunityId() != 0;
    }

    public static GAEMember editMember(GAEMember gaeMember) {
        GAEMember editedMember = new GAEMember();

        try {
            if (gaeMember.getId() > 0) {
                editedMember = new EndpointsAsyncTaskMember(gaeMember).execute().get().get(0);
            } else {
                new EndpointsAsyncTaskMember(gaeMember).execute().get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (editedMember == null)
            editedMember = new GAEMember();

        return editedMember;

    }

    public static boolean remove(Long member_id) {
        try {
            new EndpointsAsyncTaskMember(PFG_Fulltopia.QUERY_REMOVE, member_id).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
