package com.bisleri.bottleforchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = Session.getInstance(this);
        final EditText username = findViewById(R.id.editText10);
        final EditText password = findViewById(R.id.editText12);
        Button loginButton = findViewById(R.id.button12);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String username_text = username.getText().toString();
                String password_text = password.getText().toString();
                if(username_text.matches("") || password_text.matches("") ){
                    Toast.makeText(LoginActivity.this, "Kindly enter Username and Password!!", Toast.LENGTH_LONG).show();
                }else{
                    HashMap postData = new HashMap();
                    postData.put("username", username_text);
                    postData.put("password", password_text);
                    PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {

                            //Toast.makeText(loginActivity.this, output, Toast.LENGTH_LONG).show();

                            if(output.matches("wp")){
                                Toast.makeText(LoginActivity.this, "You entered wrong password", Toast.LENGTH_LONG).show();
                            }else if(output.matches("wc")){
                                Toast.makeText(LoginActivity.this, "You entered wrong credentials", Toast.LENGTH_LONG).show();
                            }else{
                                String currentString = output;
                                String[] separated = currentString.split("/");
                                // this will contain "Fruit"
                                // this will contain " they taste good"
                                //Toast.makeText(loginActivity.this, output, Toast.LENGTH_LONG).show();
                                if(separated[0].matches("nv")){
                                    session.setUserId(separated[1]);
                                    session.setCategoryId(separated[2]);
                                    session.setInvalidPin("0");
                                    resendotp(LoginActivity.this);
                                    Intent j = new Intent(LoginActivity.this, OtpActivity.class);
                                    j.putExtra("mobile_number", username_text);
                                    startActivity(j);
                                    //Toast.makeText(loginActivity.this, "Your number is not verified.", Toast.LENGTH_LONG).show();
                                }else{
                                    //session.setusename(output);
                                    session.setUserId(separated[0]);
                                    session.setCategoryId(separated[1]);
                                    session.setPin(separated[2]);
                                    session.setInvalidPin(separated[3]);

                                    //Toast.makeText(loginActivity.this, currentString, Toast.LENGTH_LONG).show();
                                    //end user dashboard ->
                                    String[] endUsersIds = { "1","2","3","4","5","10" };
                                    if(Arrays.asList(endUsersIds).contains(separated[1])){
                                        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                        startActivity(i);
                                    }else if(separated[1].matches("6")){
                                        Intent i = new Intent(LoginActivity.this, DashboardPlaticAgentActivity.class);
                                        startActivity(i);
                                    }else if(separated[1].matches("7")){
                                        Intent i = new Intent(LoginActivity.this, AggregatorActivity.class);
                                        startActivity(i);
                                    }else if(separated[1].matches("8")){
                                        Intent i = new Intent(LoginActivity.this, RecyclerActivity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(LoginActivity.this, "You do not have access tot the requested area. kindly contact admin.", Toast.LENGTH_LONG).show();
                                    }

                                }

                            }
                        }
                    });
                    task.execute(getString(R.string.server_path)+"api/check_login");
                }

            }
        });

        Button forgot_password = (Button)findViewById(R.id.textView23);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    void resendotp(Context context){
        String user_id = session.getUserId();

        HashMap postData2 = new HashMap();
        postData2.put("user_id", user_id);
        PostResponseAsyncTask task = new PostResponseAsyncTask(context, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // Toast.makeText(loginActivity.this, output, Toast.LENGTH_LONG).show();
            }
        });
        task.execute(getString(R.string.server_path)+"api/resend_otp");
    }

}
