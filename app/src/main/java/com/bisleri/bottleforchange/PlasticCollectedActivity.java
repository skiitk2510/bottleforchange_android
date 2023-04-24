package com.bisleri.bottleforchange;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PlasticCollectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastic_collected);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String user_id = Session.getInstance(PlasticCollectedActivity.this).getUserId();

        HashMap postData2 = new HashMap();
        postData2.put("user_id", user_id);
        PostResponseAsyncTask task = new PostResponseAsyncTask(PlasticCollectedActivity.this, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                TableLayout ll = findViewById(R.id.plastic_collected);
                TableRow row1= new TableRow(PlasticCollectedActivity.this);
                TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row1.setLayoutParams(lp1);
                TextView date1 = new TextView(PlasticCollectedActivity.this);
                TextView qty1 = new TextView(PlasticCollectedActivity.this);
                date1.setPadding(100, 10,0,10);
                qty1.setPadding(80, 10,0,10);
                date1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                qty1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                date1.setTypeface(null, Typeface.BOLD);
                qty1.setTypeface(null, Typeface.BOLD);
                date1.setText("Quantity");
                qty1.setText("Month");
                row1.addView(date1);
                row1.addView(qty1);
                row1.setBackgroundResource(R.drawable.row_border);
                ll.addView(row1);

                try{

                    JSONArray jsonArray = new JSONArray(output.trim());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TableRow row= new TableRow(PlasticCollectedActivity.this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(lp);
                        TextView date = new TextView(PlasticCollectedActivity.this);
                        TextView qty = new TextView(PlasticCollectedActivity.this);
                        date.setPadding(100, 10,0,10);
                        qty.setPadding(80, 10,0,10);

                        if(i==jsonArray.length()){
                            //for heading of table :
                            date.setText("Quantity");
                            qty.setText("Month");
                        }else{
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            //for data of table :
                            date.setText(explrObject.getString("total"));
                            qty.setText(explrObject.getString("monthyear"));
                        }
                        row.addView(date);
                        row.addView(qty);
                        row.setBackgroundResource(R.drawable.row_border);
                        ll.addView(row,i+1);
                    }

                }catch(Exception e){

                }

            }
        });
        task.execute(getString(R.string.server_path)+"api/get_plastic_collected");

        Button home = findViewById(R.id.button13);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(PlasticCollectedActivity.this);
            }
        });

    }

}
