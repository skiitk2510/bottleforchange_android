package com.bisleri.bottleforchange;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CredsActivity extends AppCompatActivity {

    TextView userName, password, textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creds);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String act_username= getIntent().getStringExtra("new_username");
        String act_password= getIntent().getStringExtra("new_password");
        String is_allowed= getIntent().getStringExtra("is_allowed");
        //

        userName = findViewById(R.id.textusername);
        password = findViewById(R.id.textPassword);
        textView5 = findViewById(R.id.textView5);

        userName.setText(act_username);
        password.setText(act_password);
        if(is_allowed.matches("0")) {
            textView5.setText("Note : Currently we do not serve at this pincode.");
            textView5.setTypeface(textView5.getTypeface(), Typeface.BOLD);
        }

        Button login = findViewById(R.id.button11);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //aCTIVITIES WOULD BE WITH RESPECT TO THE LOGGED IN USER I.E END USER, PLASTIC AGENT, AGGREGATOR, RECYCLER
                Intent i = new Intent(v.getContext(), DashboardActivity.class);
                startActivity(i);
            }
        });

    }

}
