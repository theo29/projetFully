package com.hesso.projetfully.bll;

import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.hesso.projetfully.GAE.EndpointsAsyncTaskCall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CallBLL {
    public static long currentCall_id = 0;

    public static List<GAECall> getAll_Calls() {
        List<GAECall> gaeCalls = new ArrayList<GAECall>();

        try {
            gaeCalls = new EndpointsAsyncTaskCall().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (gaeCalls == null) {
            gaeCalls = new ArrayList<GAECall>();
        }

        return gaeCalls;
    }

    // list of Call's object for a community
    public static List<GAECall> getAll_CallsOfCommunity(long community_id) {
        List<GAECall> gaeCalls = new ArrayList<GAECall>();
        List<GAECall> allCalls = getAll_Calls();
        for (GAECall gaeM : allCalls) {
            if (gaeM.getCommunityId().equals(community_id)) {
                gaeCalls.add(gaeM);
            }
        }

        if (gaeCalls == null) gaeCalls = new ArrayList<GAECall>();
        return gaeCalls;
    }

    // list of Call's object for a user
    public static List<GAECall> getAll_CallsOfUser(String user_id) {
        List<GAECall> gaeCalls = new ArrayList<GAECall>();
        List<GAECall> allCalls = getAll_Calls();
        for (GAECall gaeM : allCalls) {
            if (gaeM.getIdMemberCreator().equals(user_id)) {
                gaeCalls.add(gaeM);
            }
        }

        if (gaeCalls == null) gaeCalls = new ArrayList<GAECall>();
        return gaeCalls;
    }

    public static GAECall getCallById(long call_id) {
        GAECall gaeCall = new GAECall();
        try {
            gaeCall = new EndpointsAsyncTaskCall(PFG_Fulltopia.QUERY_SELECT, call_id).execute().get().get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (gaeCall == null)
            gaeCall = new GAECall();
        return gaeCall;
    }


    public static boolean isValidCall(GAECall gaeCall) {
        if (gaeCall == null) return false;
        if (gaeCall.getIdMemberCreator() == null) return false;
        return !gaeCall.getIdMemberCreator().isEmpty();
    }

    public static GAECall editCall(GAECall gaeCall) {
        GAECall editedCall = new GAECall();

        try {
            if (gaeCall.getId() > 0) {
                editedCall = new EndpointsAsyncTaskCall(gaeCall).execute().get().get(0);
            } else {
                new EndpointsAsyncTaskCall(gaeCall).execute().get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (editedCall == null)
            editedCall = new GAECall();

        return editedCall;

    }

    public static boolean remove(Long call_id) {
        try {
            new EndpointsAsyncTaskCall(PFG_Fulltopia.QUERY_REMOVE, call_id).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean getIamAdmin(GAECall call) {
        if (call == null) return false;
        return (call.getIdMemberCreator().equals(PFG_Fulltopia.getCurrentUserID()));

    }

}
