package com.hesso.projetfully;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddLeaveCallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leave_calls);

        // enable the app icon as the up buton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
