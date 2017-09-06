package com.hesso.projetfully.GAE;
import android.os.AsyncTask;
import android.util.Log;

import com.example.theop.myapplication.backend.gAECommunityApi.GAECommunityApi;
import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EndpointsAsyncTaskCommunity extends AsyncTask<Void, Void, List<GAECommunity>> {
    private static final String TAG = EndpointsAsyncTaskCommunity.class.getName();
    private static GAECommunityApi gaeCommunityApi = null;

    private GAECommunity gaeCommunity = null;
    private List<GAECommunity> gaeCommunities = null;
    private int queryAction;
    private long community_id;

    public EndpointsAsyncTaskCommunity() {
        this.queryAction = 0;
        this.community_id = 0;
    }

    public EndpointsAsyncTaskCommunity(GAECommunity gaeCommunity) {
        this.gaeCommunity = gaeCommunity;
        this.queryAction = 0;
        this.community_id = 0;
    }

    public EndpointsAsyncTaskCommunity(List<GAECommunity> gaeCommunities) {
        this.gaeCommunities = gaeCommunities;
        this.queryAction = 0;
        this.community_id = 0;
    }

    public EndpointsAsyncTaskCommunity(int queryAction, long community_id) {
        this.queryAction = queryAction;
        this.community_id = community_id;
    }


    @Override
    protected List<GAECommunity> doInBackground(Void... params) {
        if (gaeCommunityApi == null) {
            // Only do this once
            GAECommunityApi.Builder builder = new GAECommunityApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            gaeCommunityApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            switch (queryAction) {
                case PFG_Fulltopia.QUERY_SELECT:
                    return find_GAECommunityById(community_id);
                case PFG_Fulltopia.QUERY_REMOVE:
                    gaeCommunityApi.remove(community_id).execute();
                    Log.i(TAG, "deleted gaeCommunity " + community_id);
                    break;
                default:
                    // For instance EDIT
                    if (gaeCommunities != null) {
                        // insert multiple
                        insert_All_Items();
                    } else if (gaeCommunity != null) {
                        if (gaeCommunity.getId() > 0) {
                            // update
                            gaeCommunityApi.update(gaeCommunity.getId(), gaeCommunity).execute();
                            Log.i(TAG, "update gaeCommunity " + gaeCommunity.getId());
                        } else {
                            // insert
                            gaeCommunity.setId(null);
                            gaeCommunityApi.insert(gaeCommunity).execute();
//                            Log.i(TAG, "insert gaeCommunity " + gaeCommunity.getId());
                            return new ArrayList<GAECommunity>();
                        }
                        return find_GAECommunityById(gaeCommunity.getId());
                    }
                    // and for instance return the list of all items
                    return gaeCommunityApi.list().execute().getItems();
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAECommunity>();
        }
        return new ArrayList<GAECommunity>();
    }

    private List<GAECommunity> find_GAECommunityById(long _community_id) throws IOException {
        if (_community_id == 0) {
            gaeCommunity = new GAECommunity();
        } else {
            gaeCommunity = gaeCommunityApi.get(_community_id).execute();
        }

        gaeCommunities = new ArrayList<GAECommunity>();
        gaeCommunities.add(gaeCommunity);

        return gaeCommunities;

    }

    private void insert_All_Items() throws IOException {
        // delete all already existant items
        List<GAECommunity> lstCommunity = gaeCommunityApi.list().execute().getItems();
        if (lstCommunity != null) {
            for (GAECommunity gae : lstCommunity) {
                gaeCommunityApi.remove(gae.getId()).execute();
                Log.i(TAG, "deleted gaeCommunity " + gae.getId());
            }
        }
        // add all new item
        for (GAECommunity gae : gaeCommunities) {
            gaeCommunityApi.insert(gae).execute();
            Log.i(TAG, "insert gaeCommunity " + gae.getId());
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAECommunity> result) {
//            for (GAECommunity gae : result) {
//                //Log.i(TAG, "Community : " + gaeuser.getName() + " " + gaeuser.getLastname());
//                Log.i(TAG, "Community : " + gae.getDescriptionLong());
//            }
    }
}

