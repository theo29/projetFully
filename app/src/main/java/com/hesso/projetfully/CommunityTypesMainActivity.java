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

//import com.example.gci.mylibraryapp.db.adapter.CommunityTypeDataSource;
//import com.example.gci.mylibraryapp.db.object.CommunityType;
import com.example.theop.myapplication.backend.gAECommunityTypeApi.model.GAECommunityType;

import java.util.List;

//import static com.example.gci.mylibraryapp.bll.PFG_LibraryApp.MENU_REMOVE;
//import static com.example.gci.mylibraryapp.bll.PFG_LibraryApp.MENU_SELECT;
//import static com.example.gci.mylibraryapp.bll.PFG_LibraryApp.MODIFY_WRITER;

public class CommunityTypesMainActivity extends AppCompatActivity {
    public static final String EXTRA_COMMUNITYTYPE_ID = "com.hesso.projetfully.COMMUNITYTYPE_ID";
    private List<GAECommunityType> communitytypes;
    private GAECommunityType communitytype;
    private Intent intentCommunityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communitytypes_main);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Source de données
//        CommunityTypeDataSource communitytypeDS = new CommunityTypeDataSource(this);
//        communitytypes = communitytypeDS.getAllCommunityTypes();
// intent pour detail
//        intentCommunityType = new Intent(this, CommunityTypesPageActivity.class);

        ArrayAdapter<GAECommunityType> adapter = new ArrayAdapter<GAECommunityType>(this, R.layout.listview_communitytypes_layout, communitytypes) {
            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.listview_communitytypes_layout, parent, false);
                } else {
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.textView_communitytypes);
                textView1.setText(communitytypes.get(position).toString());

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
                startActivity_CommunityTypeSelected(position);
            }
        });

        //Configuration onLongPress pour modifier ou supprimer un communitytype.
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int position, long id) {
//                Toast.makeText(CommunityTypesMainActivity.this, "You have selected: " + communitytypes.get(position).toString(), Toast.LENGTH_LONG).show();
                registerForContextMenu(list);
                return false;
            }
        });

    }

    //start CommunityTypePageActivit of choosed communitytype
    private void startActivity_CommunityTypeSelected(int position) {
        intentCommunityType.putExtra(EXTRA_COMMUNITYTYPE_ID, communitytypes.get(position).getId());
        startActivity(intentCommunityType);
    }

    //start CommunityTypeEditActivit of choosed communitytype into the ContextMenu
    private void startActivity_CommunityTypeEditSelected(int position) {
        // Toast.makeText(CustomersMainActivity.this, "You have selected: " + customers.get(position).toString(), Toast.LENGTH_LONG).show();
//        communitytype = new CommunityTypeDataSource(this).getCommunityTypeById(communitytypes.get(position).getId());
//        Intent intent = new Intent(this, CommunityTypesEditActivity.class);
//        intent.putExtra(MODIFY_WRITER, communitytype);
//        startActivity(intent);

    }

    //Crate a ContextMenu for edit or delete the item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle(getResources().getString(R.string.contextmenu));
//        menu.add(0, MENU_SELECT, 0, getResources().getString(R.string.edit) + " " + getResources().getString(R.string.communitytype));
//        menu.add(0, MENU_REMOVE, 0, getResources().getString(R.string.delete));
    }
/*
    //Configuration des différentes actions possible
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            //case 1: we start edit activity
            case MENU_SELECT:
//                startActivity_CommunityTypeEditSelected(info.position);
//                return true;
//            //we delete the item
//            case MENU_REMOVE:
//                //For be sur, we show an alert dialog if yes or not, the user want to delete the item
//                new AlertDialog.Builder(this)
//                        .setTitle(getResources().getString(R.string.delete_sur))
//                        .setMessage(getResources().getString(R.string.delete_question))
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                delete_item(info.position);
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                return;
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//            default:
//                return super.onContextItemSelected(item);
//        }
    }
*/
    // Delete a communitytype by option menu
    public void delete_item(int position) {
        String title = communitytypes.get(position).toString();
//        CommunityTypeDataSource communitytypeDS = new CommunityTypeDataSource(this);
//        communitytypeDS.deleteCommunityType(communitytypes.get(position).getId());
//        Toast.makeText(this, getResources().getString(R.string.deleted) + title, Toast.LENGTH_SHORT).show();
        refresh_Activity();
    }

    private void refresh_Activity() {
        Intent refresh = new Intent(this, CommunityTypesMainActivity.class);
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

    // load a new activity for add a new communitytype
    public void showCommunityTypesAdd(View view) {
//        // TODO: 17.11.2016
//        Intent showCommunityTypesAdd = new Intent(this, CommunityTypesEditActivity.class);
//        startActivity(showCommunityTypesAdd);
    }

}
