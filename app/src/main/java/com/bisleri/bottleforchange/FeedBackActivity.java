package com.bisleri.bottleforchange;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.HashMap;

public class FeedBackActivity extends AppCompatActivity {
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        session = Session.getInstance(this);
        final EditText et1 = findViewById(R.id.editText22);

        Button bt3 = findViewById(R.id.button48);

        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user_id =session.getUserId();
                String fb = et1.getText().toString();

                if(fb.matches("")){
                    Toast.makeText(FeedBackActivity.this, "Please enter your feedback", Toast.LENGTH_LONG).show();
                    return;
                }
                if(fb.length()>2000){
                    Toast.makeText(FeedBackActivity.this, "Only 2000 characters allowed.", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap postData = new HashMap();
                postData.put("user_id", user_id);
                postData.put("feedback_comment", fb);
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(FeedBackActivity.this, postData, new AsyncResponse() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void processFinish(String output) {
                        if(output.matches("success")){
                            Intent intent = new Intent(FeedBackActivity.this, SuccessActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(FeedBackActivity.this, output, Toast.LENGTH_LONG).show();
                        }


                    }
                });
                task2.execute(getString(R.string.server_path)+"api/submit_feedback");
            }
        });

        Button bt4 = findViewById(R.id.button49);

        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(FeedBackActivity.this);
            }
        });
    }
}
