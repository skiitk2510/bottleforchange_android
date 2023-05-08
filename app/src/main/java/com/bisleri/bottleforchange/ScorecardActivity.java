package com.bisleri.bottleforchange;

import static com.bisleri.bottleforchange.R.layout.activity_scorecard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ScorecardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_scorecard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_region);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.state, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String region = spinner.getSelectedItem().toString();
                if("All India".equals(region)) {
                    region = "all";
                }
                getScoreCard(region);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // dummy impl
            }
        });

        Button home = findViewById(R.id.button41);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(ScorecardActivity.this);
            }
        });

    }

    private void getScoreCard(String region) {

        String user_id = Session.getInstance(ScorecardActivity.this).getUserId();

        HashMap requestBody = new HashMap();
        requestBody.put("user_id", user_id);
        requestBody.put("state", region);

        PostResponseAsyncTask task = new PostResponseAsyncTask(ScorecardActivity.this, requestBody, new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                TableLayout ll =  findViewById(R.id.tableViewScore);
                ll.removeAllViews();
                TableRow row1= new TableRow(ScorecardActivity.this);
                TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row1.setLayoutParams(lp1);
                TextView date1 = new TextView(ScorecardActivity.this);
                TextView qty1 = new TextView(ScorecardActivity.this);
                date1.setPadding(20, 10,0,10);
                qty1.setPadding(150, 10,0,10);
                date1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                qty1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                date1.setTypeface(null, Typeface.BOLD);
                qty1.setTypeface(null, Typeface.BOLD);
                date1.setText("Name");
                qty1.setText("Qty Submitted");
                row1.addView(date1);
                row1.addView(qty1);
                row1.setBackgroundResource(R.drawable.row_border);
                ll.addView(row1);

                try{
                    JSONArray jsonArray = new JSONArray(output.trim());
                    TextView txt = findViewById(R.id.no_data_found);
                    if(jsonArray.length() > 0) {
                        txt.setText("");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            TableRow row= new TableRow(ScorecardActivity.this);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            TextView date = new TextView(ScorecardActivity.this);
                            TextView qty = new TextView(ScorecardActivity.this);
                            date.setPadding(20, 10,0,10);
                            qty.setPadding(150, 10,0,10);

                            if(i==jsonArray.length()){

                            }else{
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                //for data of table :
                                date.setText(explrObject.getString("name"));
                                qty.setText(explrObject.getString("total")+" KG");
                            }
                            row.addView(date);
                            row.addView(qty);
                            row.setBackgroundResource(R.drawable.row_border);
                            ll.addView(row,i+1);
                        }
                    }  else {
                        Log.d("Score", "Empty: ");
                        txt.setText("No Data Found");
                    }
                }catch(Exception e){
                    Log.d("SCORE_CARD", "Exception: ", e);
                }
            }
        });
        task.execute(getString(R.string.server_path)+"api/get_scorecard");
    }
}
