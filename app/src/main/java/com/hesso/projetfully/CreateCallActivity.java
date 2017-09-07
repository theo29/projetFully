package com.hesso.projetfully;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.example.theop.myapplication.backend.gAECallApi.model.GAECall;
import com.example.theop.myapplication.backend.gAECommunityApi.model.GAECommunity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hesso.projetfully.bll.CallBLL;
import com.hesso.projetfully.bll.PFG_Fulltopia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hesso.projetfully.bll.PFG_Fulltopia.EDIT_MODE_NEW;
import static com.hesso.projetfully.bll.PFG_Fulltopia.MODIFY_COMMUNITY;

public class CreateCallActivity extends AppCompatActivity {
    private List<GAECommunity> communitys = new ArrayList<GAECommunity>();
    private GAECommunity community;
    private FirebaseAuth firebaseAuth;
    private Button btnCreate;
    private long currentId;
    private Intent intent;
    private String currentEditMode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_create_call);

        // set default mode
        currentEditMode = EDIT_MODE_NEW;




        //instanciate user
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        // Source de données
        //communitys = PFG_Fulltopia.getUser_Community();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        btnCreate = (Button) findViewById(R.id.btncreate);

        ArrayAdapter<GAECommunity> adapter = new ArrayAdapter<GAECommunity>(this, R.layout.activity_create_call, communitys) {
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
                TextView textView1 = (TextView) view.findViewById(R.id.textView_community);
                textView1.setText(communitys.get(position).getName());

                return view;
            }
        };

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //ListeView handler
        //

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText;

                GAECall call = new GAECall();

                editText = (EditText) findViewById(R.id.Description);
                call.setDescription(editText.getText().toString());


                editText = (EditText) findViewById(R.id.date) ;
                call.setDateend(editText.getText().toString());


                editText = (EditText) findViewById(R.id.lieu);
                call.setLieu(editText.getText().toString());

                call.setIdMemberCreator(firebaseAuth.getCurrentUser().getUid());

                //CallBLL.editCall(call);
                /*
        * Add new gaeCommunity, if the current mode is EDIT_MODE_NEW*/
               if (currentEditMode == EDIT_MODE_NEW) {
                    // uId current User
                    call.setId((long) 0);
                    CallBLL.editCall(call);
                    Toast.makeText(CreateCallActivity.this,"Added",Toast.LENGTH_LONG).show();
//            Toast.makeText(this, gaeCommunity.getName()+"; idUser="+gaeCommunity.getIdUserAdmin()+"; id="+gaeCommunity.getId()+"; idCommunityType="+gaeCommunity.getIdCommunityType(), Toast.LENGTH_LONG).show();
                } else {
                    // show a toast for simulate
                    if (CallBLL.editCall(call).getId() > 0)
                        Toast.makeText(CreateCallActivity.this, "error", Toast.LENGTH_LONG).show();
                }



                /*Intent intent = new Intent(CreateCallActivity.this, UserCallActivity.class);
                startActivity(intent);*/

            }
        });

    }
}
