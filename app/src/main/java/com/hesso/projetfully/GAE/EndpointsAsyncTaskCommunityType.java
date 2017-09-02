package com.hesso.projetfully.GAE;
import android.os.AsyncTask;
import android.util.Log;

import com.example.theop.myapplication.backend.gAECommunityTypeApi.GAECommunityTypeApi;
import com.example.theop.myapplication.backend.gAECommunityTypeApi.model.GAECommunityType;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class EndpointsAsyncTaskCommunityType extends AsyncTask<Void, Void, List<GAECommunityType>> {
    private static final String TAG = EndpointsAsyncTaskCommunityType.class.getName();
    private static GAECommunityTypeApi gaeCommunityTypeApi = null;
    private GAECommunityType gaeCommunityType = null;
    private List<GAECommunityType> gaeCommunityTypes = null;

    public EndpointsAsyncTaskCommunityType() {
    }

    public EndpointsAsyncTaskCommunityType(GAECommunityType gaeCommunityType) {
        this.gaeCommunityType = gaeCommunityType;
    }

    public EndpointsAsyncTaskCommunityType(List<GAECommunityType> gaeCommunityTypes) {
        this.gaeCommunityTypes = gaeCommunityTypes;
    }

    public EndpointsAsyncTaskCommunityType(String list) {

    }


    @Override
    protected List<GAECommunityType> doInBackground(Void... params) {

        if (gaeCommunityTypeApi == null) {
            // Only do this once
            GAECommunityTypeApi.Builder builder = new GAECommunityTypeApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            gaeCommunityTypeApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (gaeCommunityTypes != null) {
                // delete all already existant users
                List<GAECommunityType> lstCommunityType = gaeCommunityTypeApi.list().execute().getItems();
                if (lstCommunityType != null) {
                    for (GAECommunityType gae : lstCommunityType) {
                        gaeCommunityTypeApi.remove(gae.getId()).execute();
                        Log.i(TAG, "deleted gaeuser " + gae.getId());
                    }
                }
                // add all new users
                for (GAECommunityType gae : gaeCommunityTypes) {
                    gaeCommunityTypeApi.insert(gae).execute();
                    Log.i(TAG, "insert gaeuser " + gae.getId());
                }
            } else if (gaeCommunityType != null) {
                gaeCommunityTypeApi.insert(gaeCommunityType).execute();
                Log.i(TAG, "insert gaeuser " + gaeCommunityType.getId());
            }

            // and for instance return the list of all items
            //       return gaeCommunityTypeApi.list().execute().getItems();
            List<GAECommunityType> lstCommunityType = gaeCommunityTypeApi.list().execute().getItems();
            return lstCommunityType;

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAECommunityType>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAECommunityType> result) {
        if (result != null) {
/*
            for (GAECommunityType gaeuser : result) {
                //Log.i(TAG, "CommunityType : " + gaeuser.getName() + " " + gaeuser.getLastname());
                Log.i(TAG, "CommunityType : " + gaeuser.toString());
            }
  */
            PFG_Fulltopia.gaeCommunityTypes = result;
        }else{
            PFG_Fulltopia.gaeCommunityTypes = new ArrayList<GAECommunityType>();
        }

    }
}

