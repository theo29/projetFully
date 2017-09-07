package com.hesso.projetfully;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hesso.projetfully.bll.PFG_Fulltopia;

//last test
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button myCallsButton;
    private Button CallsButton;
    // commentaire test GCI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCallsButton = (Button) findViewById(R.id.myCallsButton);
        myCallsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserCallActivity.class);
                startActivity(intent);
            }
        });
        CallsButton = (Button) findViewById(R.id.CallsButton);
        CallsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLeaveCallsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void btnClickCommunityType(View view) {
        Intent intent = new Intent(MainActivity.this, CommunityTypesMainActivity.class);
        startActivity(intent);
    }

    public void btnClickCommunity(View view) {
        Intent intent = new Intent(MainActivity.this, CommunityMainActivity.class);
        startActivity(intent);
    }

    public void onClickTestGius(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.testdataTitle))
                .setMessage(getResources().getString(R.string.testdataQuestion))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "backup en cours...", Toast.LENGTH_SHORT).show();
                        PFG_Fulltopia.test_Add_DATA();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    //Théo
    //OnCLick go to NotificationLayout
    public void onClickBackToSelectActionFromLogin(View w){
        Intent intent = new Intent(this,NotificationActivity.class);
        startActivity(intent);
    }
}
