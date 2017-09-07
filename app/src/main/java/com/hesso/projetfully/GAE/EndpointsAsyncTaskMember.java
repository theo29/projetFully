package com.hesso.projetfully.GAE;

import android.os.AsyncTask;
import android.util.Log;

import com.example.theop.myapplication.backend.gAEMemberApi.GAEMemberApi;
import com.example.theop.myapplication.backend.gAEMemberApi.model.GAEMember;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EndpointsAsyncTaskMember extends AsyncTask<Void, Void, List<GAEMember>> {
    private static final String TAG = EndpointsAsyncTaskMember.class.getName();
    private static GAEMemberApi gaeMemberApi = null;

    private GAEMember gaeMember = null;
    private List<GAEMember> gaeMembers = null;
    private int queryAction;
    private long member_id;

    public EndpointsAsyncTaskMember() {
        this.queryAction = 0;
        this.member_id = 0;
    }

    public EndpointsAsyncTaskMember(GAEMember gaeMember) {
        this.gaeMember = gaeMember;
        this.queryAction = 0;
        this.member_id = 0;
    }

    public EndpointsAsyncTaskMember(List<GAEMember> gaeMembers) {
        this.gaeMembers = gaeMembers;
        this.queryAction = 0;
        this.member_id = 0;
    }

    public EndpointsAsyncTaskMember(int queryAction, long member_id) {
        this.queryAction = queryAction;
        this.member_id = member_id;
    }


    @Override
    protected List<GAEMember> doInBackground(Void... params) {
        if (gaeMemberApi == null) {
            // Only do this once
            GAEMemberApi.Builder builder = new GAEMemberApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl(PFG_Fulltopia._HTTPS_APP_ID_)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            gaeMemberApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            switch (queryAction) {
                case PFG_Fulltopia.QUERY_SELECT:
                    return find_GAEMemberById(member_id);
                case PFG_Fulltopia.QUERY_REMOVE:
                    gaeMemberApi.remove(member_id).execute();
                    Log.i(TAG, "deleted gaeMember " + member_id);
                    break;
                default:
                    // For instance EDIT
                    if (gaeMembers != null) {
                        // insert multiple
                        insert_All_Items();
                    } else if (gaeMember != null) {
                        if (gaeMember.getId() > 0) {
                            // update
                            gaeMemberApi.update(gaeMember.getId(), gaeMember).execute();
                            Log.i(TAG, "update gaeMember " + gaeMember.getId());
                        } else {
                            // insert
                            gaeMember.setId(null);
                            gaeMemberApi.insert(gaeMember).execute();
//                            Log.i(TAG, "insert gaeMember " + gaeMember.getId());
                            return new ArrayList<GAEMember>();
                        }
                        return find_GAEMemberById(gaeMember.getId());
                    }
                    // and for instance return the list of all items
                    return gaeMemberApi.list().execute().getItems();
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAEMember>();
        }
        return new ArrayList<GAEMember>();
    }

    private List<GAEMember> find_GAEMemberById(long _member_id) throws IOException {
        if (_member_id == 0) {
            gaeMember = new GAEMember();
        } else {
            gaeMember = gaeMemberApi.get(_member_id).execute();
        }

        gaeMembers = new ArrayList<GAEMember>();
        gaeMembers.add(gaeMember);

        return gaeMembers;

    }

    private void insert_All_Items() throws IOException {
        // delete all already existant items
        List<GAEMember> lstMember = gaeMemberApi.list().execute().getItems();
        if (lstMember != null) {
            for (GAEMember gae : lstMember) {
                gaeMemberApi.remove(gae.getId()).execute();
                Log.i(TAG, "deleted gaeMember " + gae.getId());
            }
        }
        // add all new item
        for (GAEMember gae : gaeMembers) {
            gaeMemberApi.insert(gae).execute();
            Log.i(TAG, "insert gaeMember " + gae.getId());
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAEMember> result) {
//            for (GAEMember gae : result) {
//                //Log.i(TAG, "Member : " + gaeuser.getName() + " " + gaeuser.getLastname());
//                Log.i(TAG, "Member : " + gae.getDescriptionLong());
//            }
    }
}

