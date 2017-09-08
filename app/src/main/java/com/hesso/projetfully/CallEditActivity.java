package com.hesso.projetfully;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.hesso.projetfully.bll.CallBLL;
import com.hesso.projetfully.bll.CommunityBLL;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import static com.hesso.projetfully.bll.PFG_Fulltopia.EDIT_MODE_NEW;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_CALL;

public class CallEditActivity extends AppCompatActivity {

    private GAECall gaeCall = new GAECall();

    private long currentId;
    private Intent intent;
    private String currentEditMode;
    private GAECommunity currentCommunity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_edit);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set default mode
        currentEditMode = EDIT_MODE_NEW;
        currentCommunity = CommunityBLL.getCommunityById(CommunityBLL.currentCommunity_id);
        //get  intent for the call
        intent = getIntent();
        if (intent.hasExtra(MODIFY_CALL)) {
            currentEditMode = MODIFY_CALL;
            currentId = intent.getLongExtra(MODIFY_CALL, 0);

            // if not id then application's current id
            if (currentId == 0)
                currentId = CallBLL.currentCall_id;

//        Toast.makeText(this, "I receveid a call id: " + call_id, Toast.LENGTH_LONG).show();

            // get call from Datasource
//        call = new CallDataSource(this).getCallById(call_id);
            gaeCall = CallBLL.getCallById(currentId);

            // set current id for default
            CallBLL.currentCall_id = gaeCall.getId();

//        Toast.makeText(this, "I receveid a call: " + call.toString(), Toast.LENGTH_LONG).show();
            currentId = gaeCall.getId();

            TextView title_page = (TextView) findViewById(R.id.new_call);
            title_page.setText(R.string.modify_call);

            EditText editText;
            editText = (EditText) findViewById(R.id.descriptionCE);
            editText.setText(gaeCall.getDescription());

            editText = (EditText) findViewById(R.id.dateendCE);
            editText.setText(gaeCall.getDateend());

            editText = (EditText) findViewById(R.id.lieuCE);
            editText.setText(gaeCall.getLieu());

        }

    }

    public void onValidate_Call(View view) {
        EditText editText;

//        gaeCall.setId(currentId);
        gaeCall.setIdMemberCreator(PFG_Fulltopia.getCurrentUserID());
        gaeCall.setCommunityId(CommunityBLL.currentCommunity_id);
        editText = (EditText) findViewById(R.id.dateendCE);
        gaeCall.setDateend(editText.getText().toString());

        editText = (EditText) findViewById(R.id.descriptionCE);
        gaeCall.setDescription(editText.getText().toString());

        editText = (EditText) findViewById(R.id.lieuCE);
        gaeCall.setLieu(editText.getText().toString());
        // check if this is a valid entry
        if (!CallBLL.isValidCall(gaeCall)) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.error))
                    .setMessage(getResources().getString(R.string.Call_control))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }
        /*Giuseppe 16.11.2016
        * Add new gaeCall, if the current mode is EDIT_MODE_NEW*/
        if (currentEditMode == EDIT_MODE_NEW) {
            // uId current User
            gaeCall.setId((long) 0);
            gaeCall.setIdMemberCreator(PFG_Fulltopia.getCurrentUserID());
            gaeCall.setCommunityId(CommunityBLL.currentCommunity_id);
            CallBLL.editCall(gaeCall);
            Toast.makeText(this, getResources().getString(R.string.new_gaeCall_added), Toast.LENGTH_LONG).show();
//            Toast.makeText(this, gaeCall.getName()+"; idUser="+gaeCall.getIdUserAdmin()+"; id="+gaeCall.getId()+"; idCallType="+gaeCall.getIdCallType(), Toast.LENGTH_LONG).show();
        } else {
            // show a toast for simulate
            if (CallBLL.editCall(gaeCall).getId() > 0)
                Toast.makeText(this, getResources().getString(R.string.update_gaeCall), Toast.LENGTH_LONG).show();
        }

        // return back (like up button)
        NavUtils.navigateUpFromSameTask(this);

    }
}
