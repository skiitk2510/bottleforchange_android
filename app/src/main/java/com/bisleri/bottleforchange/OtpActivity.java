package com.bisleri.bottleforchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.HashMap;

public class OtpActivity extends AppCompatActivity {

    EditText et1;
    Button submit;
    TextView tv, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et1 =  findViewById(R.id.editText);

        submit = findViewById(R.id.button10);

        tv = findViewById(R.id.textView3);
        tv2 = findViewById(R.id.textView2);

        String number = getIntent().getStringExtra("mobile_number");

        String str = "You will receive an OTP on your registered number - "+number+". Do not close the app.";
        tv2.setText(str);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String opt_no = et1.getText().toString();
                if(opt_no.length()!=6){
                    Toast.makeText(getBaseContext(), "Please enter valid otp", Toast.LENGTH_LONG).show();
                }else{
                    String username = Session.getInstance(OtpActivity.this).getUserId();

                    HashMap postData = new HashMap();
                    postData.put("app_otp_number", opt_no);
                    postData.put("user_id", username);
                    PostResponseAsyncTask task1 = new PostResponseAsyncTask((Context) v.getContext(), postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            String[] separated = output.split("/");
                            if(separated[0].matches("done")){
                                Intent i = new Intent(OtpActivity.this, CredsActivity.class);
                                i.putExtra("new_username", separated[1]);
                                i.putExtra("new_password", separated[2]);
                                i.putExtra("is_allowed", separated[3]);
                                startActivity(i);
                            }else{
                                Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    task1.execute(getString(R.string.server_path)+"api/check_otp_verification");
                }

            }
        });

        //resend OTP
        tv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String user_id = Session.getInstance(OtpActivity.this).getUserId();

                HashMap postData2 = new HashMap();
                postData2.put("user_id", user_id);
                PostResponseAsyncTask task = new PostResponseAsyncTask((Context) v.getContext(), postData2, new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Toast.makeText(getBaseContext(), "OTP has been sent on your registered mobile", Toast.LENGTH_LONG).show();
                    }
                });
                task.execute(getString(R.string.server_path)+"api/resend_otp");
            }
        });

    }

}
