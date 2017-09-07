package com.hesso.projetfully;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.hesso.projetfully.bll.CommunityBLL;

import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_COMMUNITY;

public class CommunityPageActivity extends AppCompatActivity {
    static final String STATE_ID = "ID_COMMUNITY";
    private GAECommunity community;
    private long community_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);

        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (savedInstanceState != null)
            community_id = savedInstanceState.getLong(STATE_ID);
        else
            community_id = intent.getLongExtra(CommunityMainActivity.EXTRA_COMMUNITY_ID, 0);

        // if not id then application's current id
        if (community_id == 0)
            community_id = CommunityBLL.currentCommunity_id;

//        Toast.makeText(this, "I receveid a community id: " + community_id, Toast.LENGTH_LONG).show();

        // get community from Datasource
//        community = new CommunityDataSource(this).getCommunityById(community_id);
        community = CommunityBLL.getCommunityById(community_id);

        // set current id for default
        CommunityBLL.currentCommunity_id = community.getId();

//        Toast.makeText(this, "I receveid a community: " + community.toString(), Toast.LENGTH_LONG).show();

        TextView textView;
        textView = (TextView) findViewById(R.id.nameW_lbl);
        textView.setText(community.getName());

        textView = (TextView) findViewById(R.id.idCommunity);
        textView.setText("" + community.getId());

        textView = (TextView) findViewById(R.id.communityType);
        textView.setText("" + community.getIdCommunityType());

        textView = (TextView) findViewById(R.id.idUserAdmin);
        textView.setText("" + community.getIdUserAdmin());

        textView = (TextView) findViewById(R.id.descriptionLongC);
        textView.setText("" + community.getDescriptionLong());
        setBoutonTextJoinLeave();
    }

    private void setBoutonTextJoinLeave() {
        Button btnJoinLeave = (Button) findViewById(R.id.btnJoinLeaveCommunity);
        if (CommunityBLL.getIamMember(community))
            btnJoinLeave.setText(R.string.Leave);
        else
            btnJoinLeave.setText(R.string.joinUs);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //save the community's current id
        savedInstanceState.putLong(STATE_ID, community_id);

        //Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    // get community  for the fragments
    public GAECommunity getCommunity() {
        return community;
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
                Intent refresh = new Intent(this, CommunityPageActivity.class);
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

    //modify the selected community if longpress contextmenu
    public void modifyCommunity(View view) {

        Intent intent = new Intent(this, CommunityEditActivity.class);
        intent.putExtra(MODIFY_COMMUNITY, community.getId());
        startActivity(intent);
    }


    public void joinLeaveCommunity(View view) {
        if (CommunityBLL.getIamMember(community)) {
            Toast.makeText(this, "Leaved " + community.getName(), Toast.LENGTH_LONG).show();
            CommunityBLL.leaveCommunity(community);
        } else {
            Toast.makeText(this, "Joined " + community.getName(), Toast.LENGTH_LONG).show();
            CommunityBLL.joinCommunity(community);
        }
        setBoutonTextJoinLeave();
    }
}
