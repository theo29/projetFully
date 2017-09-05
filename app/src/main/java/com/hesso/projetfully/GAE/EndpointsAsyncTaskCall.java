package com.hesso.projetfully.GAE;

import android.os.AsyncTask;
import android.util.Log;


import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.example.theop.myapplication.backend.gAECallApi.GAECallApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axel on 05.09.2017.
 */

public class EndpointsAsyncTaskCall extends AsyncTask<Void, Void, List<GAECall>> {

    private static final String TAG = EndpointsAsyncTaskCall.class.getName();
    private static GAECallApi gaeCallApi = null;
    private GAECall gaeCall = null;
    private List<GAECall> gaeCalls = null;

    public EndpointsAsyncTaskCall() {
    }

    public EndpointsAsyncTaskCall(GAECall gaeCall) {
        this.gaeCall = gaeCall;
    }

    public EndpointsAsyncTaskCall(List<GAECall> gaeCalls) {
        this.gaeCalls = gaeCalls;
    }


    @Override
    protected List<GAECall> doInBackground(Void... params) {

        if (gaeCallApi == null) {
            // Only do this once
            GAECallApi.Builder builder = new GAECallApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            gaeCallApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (gaeCall != null) {
                // delete all already existant calls
                List<GAECall> lstCall = gaeCallApi.list().execute().getItems();
                if (lstCall != null) {
                    for (GAECall gae : lstCall) {
                        gaeCallApi.remove(gae.getId()).execute();
                        Log.i(TAG, "deleted gaeCall " + gae.getId());
                    }
                }
                // add all new calls
                for (GAECall gae : gaeCalls) {
                    gaeCallApi.insert(gae).execute();
                    Log.i(TAG, "insert gaecall " + gae.getId());
                }
            } else if (gaeCall != null) {
                gaeCallApi.insert(gaeCall).execute();
                Log.i(TAG, "insert gaeCall " + gaeCall.getId());
            }

            // and for instance return the list of all items
            //       return gaeCommunityTypeApi.list().execute().getItems();
            List<GAECall> lstCall = gaeCallApi.list().execute().getItems();
            return lstCall;

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAECall>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAECall> result) {
        for (GAECall gae : result) {
            //Log.i(TAG, "CommunityType : " + gaeuser.getName() + " " + gaeuser.getLastname());
            Log.i(TAG, "CommunityType : " + gae.getDescription());
        }
    }

}
