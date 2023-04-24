package com.bisleri.bottleforchange.pincodesearch;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bisleri.bottleforchange.CallAgentActivity;
import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.Session;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class PincodeSearchActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode_search);
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Session session =  Session.getInstance(PincodeSearchActivity.this); //in oncreate
        String user_id = session.getUserId();

        RecyclerView rcvPinAgentList = findViewById(R.id.rcv_pincode_agent_list);
        rcvPinAgentList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        final PinCodeAgentListAdapter adapter = new PinCodeAgentListAdapter();
        rcvPinAgentList.setAdapter(adapter);

        HashMap postData = new HashMap();
        postData.put("user_id", user_id);
        PostResponseAsyncTask task1 = new PostResponseAsyncTask(PincodeSearchActivity.this, postData, new AsyncResponse() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void processFinish(String output) {

                try {
                    TypeToken<ArrayList<AgentDetails>> token = new TypeToken<ArrayList<AgentDetails>>(){};
                    ArrayList<AgentDetails> list = new Gson().fromJson(output, token.getType());
                    adapter.setAdapterList(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        task1.execute(getString(R.string.server_path)+"api/get_plastic_agent");


        Button search = (Button)findViewById(R.id.button28);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Session session =  Session.getInstance(PincodeSearchActivity.this); //in oncreate
                String user_id = session.getUserId();

                TextView pincode_tv = findViewById(R.id.editText17);
                String pincode= pincode_tv.getText().toString();
                HashMap postData = new HashMap();
                postData.put("pincode", pincode);
                postData.put("user_id", user_id);
                PostResponseAsyncTask task = new PostResponseAsyncTask(PincodeSearchActivity.this, postData, new AsyncResponse() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void processFinish(String output) {
                        try {
                            TypeToken<ArrayList<AgentDetails>> token = new TypeToken<ArrayList<AgentDetails>>(){};
                            ArrayList<AgentDetails> list = new Gson().fromJson(output, token.getType());
                            adapter.setAdapterList(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                task.execute(getString(R.string.server_path)+"api/get_plastic_agent");

            }
        });

        Button submit = findViewById(R.id.button29);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (adapter.getSelectedAgent() == null){
                    Toast.makeText(PincodeSearchActivity.this, "Please select plastic agent.", Toast.LENGTH_LONG).show();
                    return ;
                }

                Session session =  Session.getInstance(PincodeSearchActivity.this);
                String user_id = session.getUserId();
                HashMap postData = new HashMap();
                postData.put("user_id", user_id);
                postData.put("to_user_id", adapter.getSelectedAgent().user_id);
                PostResponseAsyncTask task = new PostResponseAsyncTask(PincodeSearchActivity.this, postData, new AsyncResponse() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void processFinish(String output) {

                        try{
                            if (output.matches("success")) {
                                CallAgentActivity.maketIntent(context, adapter.getSelectedAgent());
                            } else {
                                Toast.makeText(PincodeSearchActivity.this, output, Toast.LENGTH_LONG).show();
                            }

                        }catch(Exception e){
                            Toast.makeText(PincodeSearchActivity.this, "Something went wrong kindly try again!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                task.execute(getString(R.string.server_path)+"api/submit_support_ticket");

            }
        });

    }

}
