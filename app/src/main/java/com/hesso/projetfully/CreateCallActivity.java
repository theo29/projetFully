package com.hesso.projetfully;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theop.myapplication.backend.gAECommunityTypeApi.model.GAECommunityType;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.util.ArrayList;
import java.util.List;

public class CreateCallActivity extends AppCompatActivity {
    private List<GAECommunityType> communitytypes = new ArrayList<GAECommunityType>();
    private GAECommunityType communitytype;
    private Intent intentCommunityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_create_call);

        // Source de données
        communitytypes = PFG_Fulltopia.getUser_Community();

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
                textView1.setText(communitytypes.get(position).getDescription());

                return view;
            }
        };

        final Spinner spiner = (Spinner) findViewById(R.id.spinner);
        spiner.setAdapter(adapter);

        //ListeView handler
        //
        /*
        spiner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //(position);
            }
        });

        //Configuration onLongPress pour modifier ou supprimer un communitytype.
        spiner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int position, long id) {
                Toast.makeText(CreateCallActivity.this, "You have selected: " + communitytypes.get(position).getDescription(), Toast.LENGTH_LONG).show();
//                registerForContextMenu(list);
                return false;
            }
        });*/
    }
}
