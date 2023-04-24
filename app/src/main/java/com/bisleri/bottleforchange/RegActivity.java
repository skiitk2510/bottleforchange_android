package com.bisleri.bottleforchange;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class RegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Button socInstitutionButton = findViewById(R.id.button7);
        Button housingSocietyButton = findViewById(R.id.button8);
        Button corporateButton = findViewById(R.id.button4);
        Button hotelsResturantsButton = findViewById(R.id.button5);
        Button eduInstitutionButton = findViewById(R.id.button3);
        Button PlasticAgentsButton = findViewById(R.id.button6);

        socInstitutionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SocialInstitutionRegActivity.class);
                startActivity(i);
            }
        });

        housingSocietyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HousingSocietyActivity.class);
                startActivity(i);
            }
        });

        corporateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CorporateRegActivity.class);
                startActivity(i);
            }
        });

        hotelsResturantsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HotelResturantReg.class);
                startActivity(i);
            }
        });

        eduInstitutionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EducationInstitutionReg.class);
                startActivity(i);
            }
        });

        PlasticAgentsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PlasticAgentAggregatorReg.class);
                startActivity(i);
            }
        });
    }

}
