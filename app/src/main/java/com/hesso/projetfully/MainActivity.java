package com.hesso.projetfully;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hesso.projetfully.bll.PFG_Fulltopia;

//last test
public class MainActivity extends AppCompatActivity {
// commentaire test GCI
    //commentaire theo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_welcome_page);
    }

    public void testDataGAE(View view) {
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
}
