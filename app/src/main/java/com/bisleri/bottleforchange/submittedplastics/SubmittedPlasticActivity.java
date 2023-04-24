package com.bisleri.bottleforchange.submittedplastics;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.Session;
import com.bisleri.bottleforchange.AuthActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.HashMap;

public class SubmittedPlasticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_plastic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Session session;//global variable
        session = Session.getInstance(SubmittedPlasticActivity.this); //in oncreate
        String user_id = session.getUserId();

        final TextView tvTotal = findViewById(R.id.tv_total_submit);
        RecyclerView rcvSubmitPlastic = findViewById(R.id.rcv_submit_list);
        rcvSubmitPlastic.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        final SubmitListAdapter adapter = new SubmitListAdapter(this);
        rcvSubmitPlastic.setAdapter(adapter);

        HashMap postData2 = new HashMap();
        postData2.put("user_id", user_id);
        PostResponseAsyncTask task = new PostResponseAsyncTask(SubmittedPlasticActivity.this, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    SubmittedPlasticResponse response = new Gson().fromJson(output,SubmittedPlasticResponse.class);
                    adapter.setAdapterList(response.content);
                    tvTotal.setText(response.total);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }
        });
        task.execute(getString(R.string.server_path)+"api/get_plastic_submitted");

        Button sphome = findViewById(R.id.sphome);

        sphome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(SubmittedPlasticActivity.this);
            }
        });
    }
}
