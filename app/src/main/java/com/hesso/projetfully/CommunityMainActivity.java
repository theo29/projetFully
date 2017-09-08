package com.hesso.projetfully;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.hesso.projetfully.bll.CommunityBLL;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.util.ArrayList;
import java.util.List;

import static com.hesso.projetfully.bll.PFG_Fulltopia.MENU_REMOVE;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MENU_SELECT;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_COMMUNITY;

//import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_WRITER;

public class CommunityMainActivity extends AppCompatActivity {
    public static final String EXTRA_COMMUNITY_ID = "com.hesso.projetfully.COMMUNITY_ID";
    private List<GAECommunity> communities = new ArrayList<GAECommunity>();
    private GAECommunity community;
    private Intent intentCommunity;
    private RadioGroup radio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_main);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set radio button
        setRadioButton();


        // Source de données
        if (PFG_Fulltopia.rbCommunity_All_OnlyJoined == 1)
            communities = CommunityBLL.getOnlyJoined_Community();
        else
            communities = CommunityBLL.getAll_Community();

        // intent pour detail
        intentCommunity = new Intent(this, CommunityPageActivity.class);

        ArrayAdapter<GAECommunity> adapter = new ArrayAdapter<GAECommunity>(this, R.layout.listview_community_layout, communities) {
            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.listview_community_layout, parent, false);
                } else {
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = view.findViewById(R.id.textView_community);
                textView1.setText(communities.get(position).getName());

                return view;
            }
        };


        //ListView
        final ListView list = findViewById(R.id.main_listview);

        list.setAdapter(adapter);

        //ListeView handler
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity_CommunitySelected(position);
            }
        });

        //Configuration onLongPress pour modifier ou supprimer un community.
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int position, long id) {
//                Toast.makeText(CommunityMainActivity.this, "You have selected: " + communities.get(position).getName(), Toast.LENGTH_LONG).show();
                registerForContextMenu(list);
                return false;
            }
        });

    }

    private void setRadioButton() {
        radio = findViewById(R.id.radioGroupCommunity);
        if (PFG_Fulltopia.rbCommunity_All_OnlyJoined == 1) {
            RadioButton radioBtn_CommunityOnlyJoined = radio.findViewById(R.id.rgCommunityJoined);
            radioBtn_CommunityOnlyJoined.setChecked(true);
        } else {
            RadioButton radioBtn_CommunityAll = radio.findViewById(R.id.rgCommunityAll);
            radioBtn_CommunityAll.setChecked(true);
        }

        // set listener
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);

                //  set preferences rbCommunity_All_OnlyJoined;
                PFG_Fulltopia.rbCommunity_All_OnlyJoined = index;

                refresh_Activity();

            }
        });


    }

    //start CommunityPageActivit of choosed community
    private void startActivity_CommunitySelected(int position) {
//        Toast.makeText(this, "You have selected: " + communities.get(position).getId() + " " + communities.get(position).getName(), Toast.LENGTH_LONG).show();
        intentCommunity.putExtra(EXTRA_COMMUNITY_ID, communities.get(position).getId());
        CommunityBLL.currentCommunity_id = communities.get(position).getId();
        startActivity(intentCommunity);
    }

    //start CommunityEditActivit of choosed community into the ContextMenu
    private void startActivity_CommunityEditSelected(int position) {
        //       Toast.makeText(this, "You have selected: " + communities.get(position).getName(), Toast.LENGTH_LONG).show();
        community = communities.get(position);

        Intent intent = new Intent(this, CommunityEditActivity.class);
        intent.putExtra(MODIFY_COMMUNITY, community.getId());
        startActivity(intent);

    }

    //Crate a ContextMenu for edit or delete the item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getResources().getString(R.string.contextmenu));
        menu.add(0, MENU_SELECT, 0, getResources().getString(R.string.edit) + " " + getResources().getString(R.string.community));
        menu.add(0, MENU_REMOVE, 0, getResources().getString(R.string.delete));
    }

    //Configuration des différentes actions possible
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            //case 1: we start edit activity
            case MENU_SELECT:
                startActivity_CommunityEditSelected(info.position);
                return true;
            //Delete the item
            case MENU_REMOVE:
                //For be sur, we show an alert dialog if yes or not, the user want to delete the item
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.delete))
                        .setMessage(getResources().getString(R.string.delete_question))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                delete_item(info.position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Delete a community by option menu
    public void delete_item(int position) {
        String title = communities.get(position).getName();
        CommunityBLL.remove(communities.get(position).getId());
        Toast.makeText(this, getResources().getString(R.string.deleted) + title, Toast.LENGTH_SHORT).show();
        refresh_Activity();
    }

    private void refresh_Activity() {
        Intent refresh = new Intent(this, CommunityMainActivity.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.
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
                refresh_Activity();
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

    // load a new activity for add a new community
    public void showCommunityAdd(View view) {
        Intent showCommunityAdd = new Intent(this, CommunityEditActivity.class);
        startActivity(showCommunityAdd);
    }

}
