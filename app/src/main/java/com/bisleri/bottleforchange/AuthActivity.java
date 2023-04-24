package com.bisleri.bottleforchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AuthActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = Session.getInstance(this);
        String s =session.getUserId();
        if(s.matches("")){
            Intent i = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            String category_id =session.getCategoryId();
            String[] endUsersIds = { "1","2","3","4","5" };
            if(Arrays.asList(endUsersIds).contains(category_id)){
                Intent i = new Intent(AuthActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }else if(category_id.matches("6")){
                Intent i = new Intent(AuthActivity.this, DashboardPlaticAgentActivity.class);
                startActivity(i);
                finish();
            }else if(category_id.matches("7")){
                Intent i = new Intent(AuthActivity.this, AggregatorActivity.class);
                startActivity(i);
                finish();
            }else if(category_id.matches("8")){
                Intent i = new Intent(AuthActivity.this, RecyclerActivity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(AuthActivity.this, s, Toast.LENGTH_LONG).show();
                //on depending on user type,  redirect to respective activity
                Intent i = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    }

    public static void goHomeIntent(Context context){
        Intent goHome = new Intent(context, AuthActivity.class);
        goHome.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(goHome);


    }

}
