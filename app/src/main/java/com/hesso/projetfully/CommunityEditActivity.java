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

import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.hesso.projetfully.bll.CommunityBLL;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import static com.hesso.projetfully.bll.PFG_Fulltopia.EDIT_MODE_NEW;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_COMMUNITY;
import static com.hesso.projetfully.bll.PFG_Fulltopia.getAll_CommunityTypes;

public class CommunityEditActivity extends AppCompatActivity {

    private GAECommunity gaeCommunity = new GAECommunity();

    private long currentId;
    private Intent intent;
    private String currentEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_edit);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set default mode
        currentEditMode = EDIT_MODE_NEW;

        //get  intent for the community
        intent = getIntent();
        if (intent.hasExtra(MODIFY_COMMUNITY)) {
            currentEditMode = MODIFY_COMMUNITY;
            currentId = intent.getLongExtra(MODIFY_COMMUNITY, 0);

            // if not id then application's current id
            if (currentId == 0)
                currentId = CommunityBLL.currentCommunity_id;

//        Toast.makeText(this, "I receveid a community id: " + community_id, Toast.LENGTH_LONG).show();

            // get community from Datasource
//        community = new CommunityDataSource(this).getCommunityById(community_id);
            gaeCommunity = CommunityBLL.getCommunityById(currentId);

            // set current id for default
            CommunityBLL.currentCommunity_id = gaeCommunity.getId();

//        Toast.makeText(this, "I receveid a community: " + community.toString(), Toast.LENGTH_LONG).show();
            currentId = gaeCommunity.getId();

            TextView title_page = (TextView) findViewById(R.id.new_community);
            title_page.setText(R.string.modify_community);

            EditText editText;
            editText = (EditText) findViewById(R.id.nameC);
            editText.setText(gaeCommunity.getName());

            editText = (EditText) findViewById(R.id.descriptionLongE);
            editText.setText(gaeCommunity.getDescriptionLong());

        }

    }

    public void onValidate_Community(View view) {
        EditText editText;

//        gaeCommunity.setId(currentId);

        editText = (EditText) findViewById(R.id.nameC);
        gaeCommunity.setName(editText.getText().toString());

        editText = (EditText) findViewById(R.id.descriptionLongE);
        gaeCommunity.setDescriptionLong(editText.getText().toString());
        // check if this is a valid entry
        if (!CommunityBLL.isValidCommunity(gaeCommunity)) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.error))
                    .setMessage(getResources().getString(R.string.Community_control))
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
        * Add new gaeCommunity, if the current mode is EDIT_MODE_NEW*/
        if (currentEditMode == EDIT_MODE_NEW) {
            // uId current User
            gaeCommunity.setId((long) 0);
            gaeCommunity.setIdUserAdmin(PFG_Fulltopia.getCurrentUserID());
            gaeCommunity.setIdCommunityType(getAll_CommunityTypes().get(1).getId());
            CommunityBLL.editCommunity(gaeCommunity);
            Toast.makeText(this, getResources().getString(R.string.new_gaeCommunity_added), Toast.LENGTH_LONG).show();
//            Toast.makeText(this, gaeCommunity.getName()+"; idUser="+gaeCommunity.getIdUserAdmin()+"; id="+gaeCommunity.getId()+"; idCommunityType="+gaeCommunity.getIdCommunityType(), Toast.LENGTH_LONG).show();
        } else {
            // show a toast for simulate
            if (CommunityBLL.editCommunity(gaeCommunity).getId() > 0)
                Toast.makeText(this, getResources().getString(R.string.update_gaeCommunity), Toast.LENGTH_LONG).show();
        }

        // return back (like up button)
        NavUtils.navigateUpFromSameTask(this);

    }
}
