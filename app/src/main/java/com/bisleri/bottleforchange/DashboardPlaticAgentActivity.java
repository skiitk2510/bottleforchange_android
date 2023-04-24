package com.bisleri.bottleforchange;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bisleri.bottleforchange.submittedplastics.SubmittedPlasticActivity;
import com.bisleri.bottleforchange.tickets.PATicketsActivity;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.HashMap;

public class DashboardPlaticAgentActivity extends AppCompatActivity {

    private Session session;
    private Button openTicketsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_platic_agent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = Session.getInstance(this);
        openTicketsButton= findViewById(R.id.button18);
        openTicketsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardPlaticAgentActivity.this, PATicketsActivity.class);
                startActivity(i);
            }
        });
        //submitPlastic
        findViewById(R.id.button19).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardPlaticAgentActivity.this, PlasticAgentSubmitActivity.class);
                i.putExtra("category_id", "1,2,3,4,5,10");
                startActivity(i);
            }
        });
        //submitted Plastic
        findViewById(R.id.button20).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DashboardPlaticAgentActivity.this, SubmittedPlasticActivity.class);
                startActivity(i);
            }
        });

        //logout
        findViewById(R.id.button37).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.setUserId("");
                session.setCategoryId("");
                Intent i = new Intent(DashboardPlaticAgentActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callTicketsCountApi(); // to get updated number on comming back
    }

    private void callTicketsCountApi() {
        HashMap postData = new HashMap();
        postData.put("user_id", session.getUserId());
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(DashboardPlaticAgentActivity.this, postData, new AsyncResponse() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void processFinish(String output) {
                openTicketsButton.setText("My Open Tickets ("+output+")");
            }
        });
        task2.execute(getString(R.string.server_path)+"api/get_pa_open_tickets_count");
    }
}
