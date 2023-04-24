package com.bisleri.bottleforchange;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class AboutBottlesForChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_bottles_for_change);
    }
}
