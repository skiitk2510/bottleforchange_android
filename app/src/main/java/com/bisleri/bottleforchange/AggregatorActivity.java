package com.bisleri.bottleforchange;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bisleri.bottleforchange.submittedplastics.SubmittedPlasticActivity;

public class AggregatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button submitPlastic = findViewById(R.id.button23);

        submitPlastic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AggregatorActivity.this, PlasticAgentSubmitActivity.class);
                i.putExtra("category_id", "6");
                startActivity(i);
            }
        });

        Button openTicketsButton= findViewById(R.id.button24);

        openTicketsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AggregatorActivity.this, PlasticCollectedActivity.class);
                startActivity(i);
            }
        });

        Button PlasticSubmittedButton= findViewById(R.id.button25);

        PlasticSubmittedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AggregatorActivity.this, SubmittedPlasticActivity.class);
                startActivity(i);
            }
        });

        Button btlo = findViewById(R.id.button45);
        btlo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Session session =  Session.getInstance(AggregatorActivity.this); //in oncreate
                session.setUserId("");
                session.setCategoryId("");
                Intent i = new Intent(AggregatorActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

}
