package com.hesso.projetfully.bll;

import com.example.theop.myapplication.backend.gAEUserApi.model.GAEUser;
import com.example.theop.myapplication.backend.gAECommunityTypeApi.model.GAECommunityType;

import com.hesso.projetfully.GAE.EndpointsAsyncTaskCommunityType;
import com.hesso.projetfully.GAE.EndpointsAsyncTaskUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GCI on 14.07.2017.
 */
public class PFG_Fulltopia {
    // options for running against local devappserver
    // - 10.0.2.2 is localhost's IP address in Android emulator
    // - turn off compression when running against local devappserver
    // if you deploy on the cloud backend, use your app name
    // such as https://<your-app-id>.appspot.com

    // local
//    public static final String _HTTPS_APP_ID_ = "http://10.0.2.2:8080/_ah/api/";
    // cloud
    public static final String _HTTPS_APP_ID_ = "https://fulltopia-173713.appspot.com/_ah/api/";

    public static void test_Add_DATA(){
        test_Add_DATA_Users();
        test_Add_DATA_CommunityTypes();

    }

    private static void test_Add_DATA_CommunityTypes() {
        List<GAECommunityType> gaeCommunityTypes;

        gaeCommunityTypes = new ArrayList<GAECommunityType>();

        // add in the GAE
        GAECommunityType gaeCommunityType = new GAECommunityType();
        gaeCommunityType.setDescription("Sport");
        gaeCommunityTypes.add(gaeCommunityType);
        // add in the GAE
        gaeCommunityType = new GAECommunityType();
        gaeCommunityType.setDescription("Music");
        gaeCommunityTypes.add(gaeCommunityType);
        // add in the GAE
        gaeCommunityType = new GAECommunityType();
        gaeCommunityType.setDescription("Art");
        gaeCommunityTypes.add(gaeCommunityType);
        // add in the GAE
        gaeCommunityType = new GAECommunityType();
        gaeCommunityType.setDescription("Hobby");
        gaeCommunityTypes.add(gaeCommunityType);
        // add in the GAE
        gaeCommunityType = new GAECommunityType();
        gaeCommunityType.setDescription("Cours");
        gaeCommunityTypes.add(gaeCommunityType);
        // add in the GAE
        gaeCommunityType = new GAECommunityType();
        gaeCommunityType.setDescription("Home");
        gaeCommunityTypes.add(gaeCommunityType);

        if (gaeCommunityTypes.size() > 0) {
            new EndpointsAsyncTaskCommunityType(gaeCommunityTypes).execute();
        }
    }

    private static void test_Add_DATA_Users() {
        List<GAEUser> gaeUsers;

        gaeUsers = new ArrayList<GAEUser>();

        GAEUser gaeUser = new GAEUser();
//        gaeUser.setId((long) 10);
        gaeUser.setEmail("g.cipri@global-office.com");
        gaeUser.setPassword("1234");
        gaeUser.setUserName("Giuseppe");
        // add in the gaeUsers
        gaeUsers.add(gaeUser);

        gaeUser = new GAEUser();
//        gaeUser.setId((long) 2);
        gaeUser.setEmail("axel@students.hes.ch");
        gaeUser.setPassword("9876");
        gaeUser.setUserName("Axel");
        // add in the gaeUsers
        gaeUsers.add(gaeUser);

        if (gaeUsers.size() > 0) {
            new EndpointsAsyncTaskUser(gaeUsers).execute();
        }
    }

}
