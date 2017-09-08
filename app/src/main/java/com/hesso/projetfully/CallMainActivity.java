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
import android.widget.TextView;
import android.widget.Toast;

import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.hesso.projetfully.bll.CallBLL;
import com.hesso.projetfully.bll.CommunityBLL;

import java.util.ArrayList;
import java.util.List;

import static com.hesso.projetfully.bll.PFG_Fulltopia.MENU_REMOVE;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MENU_SELECT;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_CALL;

public class CallMainActivity extends AppCompatActivity {
    public static final String EXTRA_CALL_ID = "com.hesso.projetfully.CALL_ID";
    private List<GAECall> calls = new ArrayList<GAECall>();
    private GAECall call;
    private Intent intentCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_main);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Source de données
        calls = CallBLL.getAll_CallsOfCommunity(CommunityBLL.currentCommunity_id);

        // intent pour detail
        intentCall = new Intent(this, CallPageActivity.class);

        ArrayAdapter<GAECall> adapter = new ArrayAdapter<GAECall>(this, R.layout.listview_call_layout, calls) {
            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.listview_call_layout, parent, false);
                } else {
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = view.findViewById(R.id.textView_call);
                textView1.setText(calls.get(position).getDescription());

                return view;
            }
        };


        //ListView
        final ListView list = (ListView) findViewById(R.id.main_listview);

        list.setAdapter(adapter);

        //ListeView handler
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity_CallSelected(position);
            }
        });

        //Configuration onLongPress pour modifier ou supprimer un call.
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int position, long id) {
//                Toast.makeText(CallMainActivity.this, "You have selected: " + calls.get(position).getName(), Toast.LENGTH_LONG).show();
                registerForContextMenu(list);
                return false;
            }
        });

    }

    //start CallPageActivit of choosed call
    private void startActivity_CallSelected(int position) {
//        Toast.makeText(this, "You have selected: " + calls.get(position).getId() + " " + calls.get(position).getName(), Toast.LENGTH_LONG).show();
        intentCall.putExtra(EXTRA_CALL_ID, calls.get(position).getId());
        CallBLL.currentCall_id = calls.get(position).getId();
        startActivity(intentCall);
    }

    //start CallEditActivit of choosed call into the ContextMenu
    private void startActivity_CallEditSelected(int position) {
        //       Toast.makeText(this, "You have selected: " + calls.get(position).getName(), Toast.LENGTH_LONG).show();
        call = calls.get(position);

        Intent intent = new Intent(this, CallEditActivity.class);
        intent.putExtra(MODIFY_CALL, call.getId());
        CallBLL.currentCall_id = call.getId();
        startActivity(intent);
    }

    //Crate a ContextMenu for edit or delete the item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getResources().getString(R.string.contextmenu));
        menu.add(0, MENU_SELECT, 0, getResources().getString(R.string.edit) + " " + getResources().getString(R.string.call));
        menu.add(0, MENU_REMOVE, 0, getResources().getString(R.string.delete));
    }

    //Configuration des différentes actions possible
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            //case 1: we start edit activity
            case MENU_SELECT:
                startActivity_CallEditSelected(info.position);
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

    // Delete a call by option menu
    public void delete_item(int position) {
        String title = calls.get(position).getDescription();
        CallBLL.remove(calls.get(position).getId());
        Toast.makeText(this, getResources().getString(R.string.deleted) + title, Toast.LENGTH_SHORT).show();
        refresh_Activity();
    }

    private void refresh_Activity() {
        Intent refresh = new Intent(this, CallMainActivity.class);
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

    // load a new activity for add a new call
    public void showCallAdd(View view) {
        Intent showCallAdd = new Intent(this, CallEditActivity.class);
        startActivity(showCallAdd);
    }

}
