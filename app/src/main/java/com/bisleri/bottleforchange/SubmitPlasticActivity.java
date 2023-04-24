package com.bisleri.bottleforchange;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bisleri.bottleforchange.pincodesearch.PincodeSearchActivity;
import com.bisleri.bottleforchange.plasticagentlist.PlasticAgentsListActivity;

public class SubmitPlasticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_plastic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button bt1 = findViewById(R.id.button26);

        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SubmitPlasticActivity.this, PincodeSearchActivity.class);
                startActivity(i);
            }
        });

        Button bt2 = findViewById(R.id.button27);

        String is_pin_set = Session.getInstance(SubmitPlasticActivity.this).isPinSet();

        if(is_pin_set.matches("1")){
            bt2.setText("List By Registered Location");
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(SubmitPlasticActivity.this, PlasticAgentsListActivity.class);
                    startActivity(i);
                }
            });
        }else{
            bt2.setText("Drop A Pin");
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(SubmitPlasticActivity.this, DropPinActivity.class);
                    startActivity(i);
                }
            });
        }

        Button feedback = findViewById(R.id.button39);
        feedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SubmitPlasticActivity.this, FeedBackActivity.class);
                startActivity(i);
            }
        });
    }

}
