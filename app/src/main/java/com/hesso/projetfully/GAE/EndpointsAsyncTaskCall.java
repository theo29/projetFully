package com.hesso.projetfully.GAE;

import android.os.AsyncTask;
import android.util.Log;

import com.example.theop.myapplication.backend.gAECallApi.GAECallApi;
import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EndpointsAsyncTaskCall extends AsyncTask<Void, Void, List<GAECall>> {
    private static final String TAG = EndpointsAsyncTaskCall.class.getName();
    private static GAECallApi gaeCallApi = null;

    private GAECall gaeCall = null;
    private List<GAECall> gaeCalls = null;
    private int queryAction;
    private long call_id;

    public EndpointsAsyncTaskCall() {
        this.queryAction = 0;
        this.call_id = 0;
    }

    public EndpointsAsyncTaskCall(GAECall gaeCall) {
        this.gaeCall = gaeCall;
        this.queryAction = 0;
        this.call_id = 0;
    }

    public EndpointsAsyncTaskCall(List<GAECall> gaeCalls) {
        this.gaeCalls = gaeCalls;
        this.queryAction = 0;
        this.call_id = 0;
    }

    public EndpointsAsyncTaskCall(int queryAction, long call_id) {
        this.queryAction = queryAction;
        this.call_id = call_id;
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
            switch (queryAction) {
                case PFG_Fulltopia.QUERY_SELECT:
                    return find_GAECallById(call_id);
                case PFG_Fulltopia.QUERY_REMOVE:
                    gaeCallApi.remove(call_id).execute();
                    Log.i(TAG, "deleted gaeCall " + call_id);
                    break;
                default:
                    // For instance EDIT
                    if (gaeCalls != null) {
                        // insert multiple
                        insert_All_Items();
                    } else if (gaeCall != null) {
                        if (gaeCall.getId() > 0) {
                            // update
                            gaeCallApi.update(gaeCall.getId(), gaeCall).execute();
                            Log.i(TAG, "update gaeCall " + gaeCall.getId());
                        } else {
                            // insert
                            gaeCall.setId(null);
                            gaeCallApi.insert(gaeCall).execute();
//                            Log.i(TAG, "insert gaeCall " + gaeCall.getId());
                            return new ArrayList<GAECall>();
                        }
                        return find_GAECallById(gaeCall.getId());
                    }
                    // and for instance return the list of all items
                    return gaeCallApi.list().execute().getItems();
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAECall>();
        }
        return new ArrayList<GAECall>();
    }

    private List<GAECall> find_GAECallById(long _call_id) throws IOException {
        if (_call_id == 0) {
            gaeCall = new GAECall();
        } else {
            gaeCall = gaeCallApi.get(_call_id).execute();
        }

        gaeCalls = new ArrayList<GAECall>();
        gaeCalls.add(gaeCall);

        return gaeCalls;

    }

    private void insert_All_Items() throws IOException {
        // delete all already existant items
        List<GAECall> lstCall = gaeCallApi.list().execute().getItems();
        if (lstCall != null) {
            for (GAECall gae : lstCall) {
                gaeCallApi.remove(gae.getId()).execute();
                Log.i(TAG, "deleted gaeCall " + gae.getId());
            }
        }
        // add all new item
        for (GAECall gae : gaeCalls) {
            gaeCallApi.insert(gae).execute();
            Log.i(TAG, "insert gaeCall " + gae.getId());
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAECall> result) {
//            for (GAECall gae : result) {
//                //Log.i(TAG, "Call : " + gaeuser.getName() + " " + gaeuser.getLastname());
//                Log.i(TAG, "Call : " + gae.getDescriptionLong());
//            }
    }
}

