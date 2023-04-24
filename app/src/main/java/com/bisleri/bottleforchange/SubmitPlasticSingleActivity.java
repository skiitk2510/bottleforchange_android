package com.bisleri.bottleforchange;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bisleri.bottleforchange.pincodesearch.AgentDetails;
import com.bisleri.bottleforchange.tickets.TicketDetails;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.HashMap;

public class SubmitPlasticSingleActivity extends AppCompatActivity {

    public static final String SELECTED_TICKET_DETAILS = "selected_ticket_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_plastic_single);

        final TicketDetails ticketDetails = getIntent().getParcelableExtra(SELECTED_TICKET_DETAILS);

        TextView tv1 = findViewById(R.id.textView19);
        tv1.setText(ticketDetails.address);

        Button submit = findViewById(R.id.button36);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String user_id = Session.getInstance(SubmitPlasticSingleActivity.this).getUserId();

                EditText hard_plastic_kg_et = findViewById(R.id.editText18);
                EditText hard_plastic_gm_et = findViewById(R.id.editText19);
                EditText soft_plastic_kg_et = findViewById(R.id.editText20);
                EditText soft_plastic_gm_et = findViewById(R.id.editText21);

                String hard_plastic_kg = hard_plastic_kg_et.getText().toString();
                String hard_plastic_gm = hard_plastic_gm_et.getText().toString();
                String soft_plastic_kg = soft_plastic_kg_et.getText().toString();
                String soft_plastic_gm = soft_plastic_gm_et.getText().toString();

                if(hard_plastic_kg.matches("") && soft_plastic_kg.matches("") && hard_plastic_gm.matches("") && soft_plastic_gm.matches("")){
                    Toast.makeText(SubmitPlasticSingleActivity.this, "Please enter plastic quantity", Toast.LENGTH_LONG).show();
                    return ;
                }


                if(hard_plastic_kg.matches("")){
                    hard_plastic_kg = "0";
                }

                if(hard_plastic_gm.matches("")){
                    hard_plastic_gm = "0";
                }

                if(soft_plastic_kg.matches("")){
                    soft_plastic_kg = "0";
                }

                if(soft_plastic_gm.matches("")){
                    soft_plastic_gm = "0";
                }




                HashMap postData = new HashMap();
                postData.put("save", "1");
                postData.put("hard_plastic_kg", hard_plastic_kg);
                postData.put("hard_plastic_gm", hard_plastic_gm);
                postData.put("soft_plastic_kg", soft_plastic_kg);
                postData.put("soft_plastic_gm", soft_plastic_gm);
                postData.put("user_id", user_id);
                //postData.put("selected_user", phone_number);
                postData.put("given_by_user_id", ticketDetails.user_id);
                postData.put("ticket_id", ticketDetails.support_ticket_id);
                PostResponseAsyncTask task3 = new PostResponseAsyncTask(SubmitPlasticSingleActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        if(output.matches("false"))
                            Toast.makeText(SubmitPlasticSingleActivity.this, output, Toast.LENGTH_LONG).show();
                        else{
                            Intent i = new Intent(SubmitPlasticSingleActivity.this, SuccessActivity.class);
                            startActivity(i);
                        }
                    }
                });
                task3.execute(getString(R.string.server_path)+"api/submit_plastic_agent");

            }
        });
    }
}
