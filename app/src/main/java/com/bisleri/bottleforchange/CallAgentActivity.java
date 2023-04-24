package com.bisleri.bottleforchange;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bisleri.bottleforchange.pincodesearch.AgentDetails;
import com.bisleri.bottleforchange.pincodesearch.PincodeSearchActivity;

public class CallAgentActivity extends AppCompatActivity {

    public static final String SELECTED_AGENT_DETAILS = "selected_agent_details";

    public static void maketIntent(Context context, AgentDetails selectedAgent) {
        Intent intent = new Intent(context, CallAgentActivity.class);
        intent.putExtra(CallAgentActivity.SELECTED_AGENT_DETAILS, selectedAgent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_agent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final AgentDetails agent = getIntent().getParcelableExtra(SELECTED_AGENT_DETAILS);

        TextView tv15 = findViewById(R.id.textView15);
        tv15.setText(agent.address);

        Button callBtn = findViewById(R.id.button30);
        callBtn.setText("Call " + agent.mobile_number);

        callBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+91" + agent.mobile_number));
                startActivity(callIntent);
            }
        });

        Button home = findViewById(R.id.button44);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(CallAgentActivity.this);
            }
        });
    }

}
