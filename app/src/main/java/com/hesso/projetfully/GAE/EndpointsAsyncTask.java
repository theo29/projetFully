package com.hesso.projetfully.GAE;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GCI on 14.07.2017.
 */
public class EndpointsAsyncTask{

}
//public class EndpointsAsyncTask  extends AsyncTask<Void, Void, List<GAEUser>> {
//    private static GAEUserApi employeeApi = null;
//    private static final String TAG = EndpointsAsyncTask.class.getName();
//    private GAEUser employee;
//
//    EndpointsAsyncTask(){}
//
//    EndpointsAsyncTask(GAEUser employee){
//        this.employee = employee;
//    }
//
//    @Override
//    protected List<GAEUser> doInBackground(Void... params) {
//
//        if(employeeApi == null){
//            // Only do this once
//            GAEUserApi.Builder builder = new GAEUserApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    // options for running against local devappserver
//                    // - 10.0.2.2 is localhost's IP address in Android emulator
//                    // - turn off compression when running against local devappserver
//                    // if you deploy on the cloud backend, use your app name
//                    // such as https://<your-app-id>.appspot.com
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
//            employeeApi = builder.build();
//        }
//
//        try{
//            // Call here the wished methods on the Endpoints
//            // For instance insert
//            if(employee != null){
//                employeeApi.insert(employee).execute();
//                Log.i(TAG, "insert employee");
//            }
//            // and for instance return the list of all employees
//            return employeeApi.list().execute().getItems();
//
//        } catch (IOException e){
//            Log.e(TAG, e.toString());
//            return new ArrayList<GAEUser>();
//        }
//    }
//
//    //This method gets executed on the UI thread - The UI can be manipulated directly inside
//    //of this method
//    @Override
//    protected void onPostExecute(List<GAEUser> result){
//
//        if(result != null) {
//            for (GAEUser employee : result) {
//                Log.i(TAG, "First name: " + employee.getFirstname() + " Last name: "
//                        + employee.getLastname());
//
//                for (Phone phone : employee.getPhones()) {
//                    Log.i(TAG, "Phone number: " + phone.getNumber() + " Type: " + phone.getType());
//                }
//            }
//        }
//    }
//
//}
