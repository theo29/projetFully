package com.hesso.projetfully;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button TestAxel;
    private Button TestGius;
    private Button TestTheo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TestAxel = (Button) findViewById(R.id.TestAxel);
        TestGius = (Button) findViewById(R.id.TestGius);
        TestTheo = (Button) findViewById(R.id.TestTheo);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        TestAxel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserCallActivity.class);
                startActivity(intent);

            }
        });


        TestGius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent communityType = new Intent(MainActivity.this, CommunityTypesMainActivity.class);
                startActivity(communityType);//Start the same Activity

            }
        });


        TestTheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void btnClickCommunityType(View view) {
        Intent communityType = new Intent(MainActivity.this, CommunityTypesMainActivity.class);
        startActivity(communityType);//Start the same Activity
    }
}
