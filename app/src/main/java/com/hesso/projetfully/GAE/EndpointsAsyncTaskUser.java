package com.hesso.projetfully.GAE;

import android.os.AsyncTask;
import android.util.Log;

import com.example.TheoP.myapplication.backend.GAEUser;
import com.example.TheoP.myapplication.backend.GAEUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GCI on 25.08.2017.
 */

public class EndpointsAsyncTaskUser extends AsyncTask<Void, Void, List<GAEUser>> {
    private static final String TAG = EndpointsAsyncTaskUser.class.getName();
    private static GAEUserApi gaeUserApi = null;
    private GAEUser gaeUser = null;
    private List<GAEUser> gaeUsers = null;

    EndpointsAsyncTaskUser() {
    }

    EndpointsAsyncTaskUser(GAEUser gaeUser) {
        this.gaeUser = gaeUser;
    }

    EndpointsAsyncTaskUser(List<GAEUser> gaeUsers) {
        this.gaeUsers = gaeUsers;
    }

    @Override
    protected List<GAEUser> doInBackground(Void... params) {

        if (gaeUserApi == null) {
            // Only do this once
            GAEUserApi.Builder builder = new GAEUserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://backup-154715.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            gaeUserApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (gaeUsers != null) {
                // delete all already existant users
                List<GAEUser> lstUser = gaeUserApi.list().execute().getItems();
                if (lstUser != null) {
                    for (GAEUser gae : lstUser) {
                        gaeUserApi.remove(gae.getId()).execute();
                        Log.i(TAG, "deleted gaeuser " + gae.getId());
                    }
                }
                // add all new users
                for (GAEUser gae : gaeUsers) {
                    gaeUserApi.insert(gae).execute();
                    Log.i(TAG, "insert gaeuser " + gae.getId());
                }
            } else if (gaeUser != null) {
                gaeUserApi.insert(gaeUser).execute();
                Log.i(TAG, "insert gaeuser " + gaeUser.getId());
            }

            // and for instance return the list of all employees
            return gaeUserApi.list().execute().getItems();

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<GAEUser>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GAEUser> result) {
        if (result != null) {
            for (GAEUser gaeuser : result) {
                //Log.i(TAG, "User : " + gaeuser.getName() + " " + gaeuser.getLastname());
                Log.i(TAG, "User : " + gaeuser.toString());
            }
        }
    }
}