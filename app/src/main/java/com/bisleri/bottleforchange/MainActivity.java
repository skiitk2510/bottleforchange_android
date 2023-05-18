package com.bisleri.bottleforchange;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.net.Uri;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button regButton, loginButton, truth, about, faq;
    ImageButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regButton = findViewById(R.id.button);
        loginButton = findViewById(R.id.button2);
        truth = findViewById(R.id.button42);
        about = findViewById(R.id.button43);
        faq = findViewById(R.id.faq);
        shareButton = findViewById(R.id.share_button);

        regButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegActivity.class);
                startActivity(i);
               // Toast.makeText(MainActivity.this, "Thank you !!", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);

            }
        });

        truth.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TruthAboutActivity.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AboutBottlesForChangeActivity.class);
                startActivity(i);
            }
        });

        faq.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), FaqActivity.class);
                startActivity(i);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "Check Out Bottle for Change App - An initiative by Bisleri Trust! https://play.google.com/store/apps/details?id=com.bisleri.bottleforchange&hl=en_IN&gl=US";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out Bottle for change app - an initiative by Bisleri Trust!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

}
