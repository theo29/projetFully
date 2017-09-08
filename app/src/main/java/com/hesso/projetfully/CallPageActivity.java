package com.hesso.projetfully;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.hesso.projetfully.bll.CallBLL;
import com.hesso.projetfully.bll.CommunityBLL;

import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_CALL;

public class CallPageActivity extends AppCompatActivity {
    static final String STATE_ID = "ID_CALL";
    private GAECall call;
    private long call_id;
    private GAECommunity currentCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_page);

        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (savedInstanceState != null)
            call_id = savedInstanceState.getLong(STATE_ID);
        else
            call_id = intent.getLongExtra(CallMainActivity.EXTRA_CALL_ID, 0);

        // if not id then application's current id
        if (call_id == 0)
            call_id = CallBLL.currentCall_id;

        Toast.makeText(this, "I receveid a call id: " + call_id, Toast.LENGTH_LONG).show();

        // get call from Datasource
//        call = new CallDataSource(this).getCallById(call_id);
        call = CallBLL.getCallById(call_id);
        currentCommunity = CommunityBLL.getCommunityById(call.getCommunityId());

        // set current id for default
        CallBLL.currentCall_id = call.getId();

//        Toast.makeText(this, "I receveid a call: " + call.toString(), Toast.LENGTH_LONG).show();

        TextView textView;
        textView = (TextView) findViewById(R.id.nameCommunity_lbl);
        textView.setText(currentCommunity.getName());

        textView = (TextView) findViewById(R.id.idDateendCP);
        textView.setText("" + call.getDateend());

        textView = (TextView) findViewById(R.id.lieuCP);
        textView.setText("" + call.getLieu());

        textView = (TextView) findViewById(R.id.descriptionCP);
        textView.setText("" + call.getDescription());
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //save the call's current id
        savedInstanceState.putLong(STATE_ID, call_id);

        //Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    // get call  for the fragments
    public GAECall getCall() {
        return call;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload:
                Intent refresh = new Intent(this, CallPageActivity.class);
                startActivity(refresh);//Start the same Activity
                finish(); //finish Activity.
                break;

            case R.id.settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);//Start the same Activity
                finish(); //finish Activity.
                break;

            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

            default:
                super.onOptionsItemSelected(item);
                break;

        }
        return true;
    }

    //modify the selected call if longpress contextmenu
    public void modifyCall(View view) {

        Intent intent = new Intent(this, CallEditActivity.class);
        intent.putExtra(MODIFY_CALL, call.getId());
        startActivity(intent);
    }

    public void onClickShowiInMap(View view){
        Intent intent = new Intent(this, CallMapsActivity.class);
        intent.putExtra(MODIFY_CALL, call.getLieu());
        startActivity(intent);
    }

}
