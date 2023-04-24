package com.bisleri.bottleforchange;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Button plasticAgent = findViewById(R.id.button31);

        plasticAgent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RecyclerActivity.this, PlasticAgentSubmitActivity.class);
                i.putExtra("category_id", "6");
                startActivity(i);
            }
        });

        Button aggregatorAgent = findViewById(R.id.button32);

        aggregatorAgent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RecyclerActivity.this, PlasticAgentSubmitActivity.class);
                i.putExtra("category_id", "7");
                startActivity(i);
            }
        });

        Button collectedPlastic = findViewById(R.id.button33);

        collectedPlastic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RecyclerActivity.this, PlasticCollectedActivity.class);
                startActivity(i);
            }
        });

        Button logout = findViewById(R.id.button34);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Session session =  Session.getInstance(RecyclerActivity.this); //in oncreate
                session.setUserId("");
                session.setCategoryId("");
                Intent i = new Intent(RecyclerActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
