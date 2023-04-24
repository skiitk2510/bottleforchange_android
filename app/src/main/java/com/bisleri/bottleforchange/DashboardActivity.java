package com.bisleri.bottleforchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.bisleri.bottleforchange.submittedplastics.SubmittedPlasticActivity;

//for corporate login category
public class DashboardActivity extends AppCompatActivity {

    Button bt0, bt1, bt2, bt3, btlo, truth, about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String invalidPincodeFlag = Session.getInstance(this).isPinInvalid();

        bt0 = findViewById(R.id.button14);

        bt0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Toast.makeText(dashboardActivity.this, invalidPincodeFlag, Toast.LENGTH_LONG).show();
                if (invalidPincodeFlag.equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                    builder.setMessage(Html.fromHtml(getString(R.string.not_present_in_pin))).setCancelable(false)
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Intent i = new Intent(DashboardActivity.this, SubmitPlasticActivity.class);
                    startActivity(i);
                }
            }
        });

        bt1 = findViewById(R.id.button15);

        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SubmittedPlasticActivity.class);
                startActivity(i);
            }
        });

        bt2 = findViewById(R.id.button16);
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, TicketActivity.class);
                startActivity(i);
            }
        });

        bt3 = findViewById(R.id.button17);
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ScorecardActivity.class);
                startActivity(i);
            }
        });

        Button fbButton = findViewById(R.id.buttonFb);
        fbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, FeedBackActivity.class);
                startActivity(i);
            }
        });

        btlo = findViewById(R.id.buttonLogout);
        btlo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Session session =  Session.getInstance(DashboardActivity.this); //in oncreate
                session.setUserId("");
                session.setCategoryId("");
                Intent i = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        truth = findViewById(R.id.buttonttapr);
        truth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TruthAboutActivity.class);
                startActivity(i);
            }
        });

        about = findViewById(R.id.buttonabfc);
        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AboutBottlesForChangeActivity.class);
                startActivity(i);
            }
        });

    }

}
