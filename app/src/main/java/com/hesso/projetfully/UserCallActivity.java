package com.hesso.projetfully;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class UserCallActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private List<GAECall> calls = new ArrayList<GAECall>();
    private GAECall call;
    private Intent intentCall;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_call);

        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //calls = PFG_Fulltopia.getCallUser();

        //instanciate user
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ArrayAdapter<GAECall> adapter = new ArrayAdapter<GAECall>(this, R.layout.listview_communitytypes_layout, calls) {
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
                textView1.setText(calls.get(position).getDescription());

                return view;
            }
        };


    }

    // load a new activity for add a new call
    public void addCall(View view) {

       Intent showCommunityTypesAdd = new Intent(this, CreateCallActivity.class);
        startActivity(showCommunityTypesAdd);
    }
}
