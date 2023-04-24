package com.bisleri.bottleforchange;

import android.content.Intent;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText  et1 = findViewById(R.id.editText25);

        Button submit = findViewById(R.id.button50);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String et1_val = et1.getText().toString();
                if(et1_val.length()!=10){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your valid registered mobile number", Toast.LENGTH_LONG).show();
                    return ;
                }

                HashMap postData = new HashMap();
                postData.put("mobile_number", et1_val);
                PostResponseAsyncTask task = new PostResponseAsyncTask(ForgotPasswordActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {

                        if(output.matches("success")){
                            Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(ForgotPasswordActivity.this, output, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                task.execute(getString(R.string.server_path)+"api/forgot_password");
            }
        });

        Button gotologin = findViewById(R.id.button51);

        gotologin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
