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
    private List<GAECommunity> gaeCommunitys = null;

    public EndpointsAsyncTaskCommunity() {
    }

    public EndpointsAsyncTaskCommunity(GAECommunity gaeCommunity) {
        this.gaeCommunity = gaeCommunity;
    }

    public EndpointsAsyncTaskCommunity(List<GAECommunity> gaeCommunitys) {
        this.gaeCommunitys = gaeCommunitys;
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
            // For instance insert
            if (gaeCommunitys != null) {
                // delete all already existant users
                List<GAECommunity> lstCommunity = gaeCommunityApi.list().execute().getItems();
                if (lstCommunity != null) {
                    for (GAECommunity gae : lstCommunity) {
                        gaeCommunityApi.remove(gae.getId()).execute();
                        Log.i(TAG, "deleted gaeuser " + gae.getId());
                    }
                }
                // add all new users
                for (GAECommunity gae : gaeCommunitys) {
                    gaeCommunityApi.insert(gae).execute();
                    Log.i(TAG, "insert gaeuser " + gae.getId());
                }
            } else if (gaeCommunity != null) {
                gaeCommunityApi.insert(gaeCommunity).execute();
                Log.i(TAG, "insert gaeuser " + gaeCommunity.getId());
            }

            // and for instance return the list of all items
            //       return gaeCommunityApi.list().execute().getItems();
            List<GAECommunity> lstCommunity = gaeCommunityApi.list().execute().getItems();
            return lstCommunity;

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAECommunity>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAECommunity> result) {
            for (GAECommunity gae : result) {
                //Log.i(TAG, "Community : " + gaeuser.getName() + " " + gaeuser.getLastname());
                Log.i(TAG, "Community : " + gae.getDescriptionLong());
            }
    }
}

