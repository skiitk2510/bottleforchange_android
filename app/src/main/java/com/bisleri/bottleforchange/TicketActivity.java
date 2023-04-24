package com.bisleri.bottleforchange;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class TicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String user_id = Session.getInstance(TicketActivity.this).getUserId();

        HashMap postData2 = new HashMap();
        postData2.put("user_id", user_id);
        PostResponseAsyncTask task = new PostResponseAsyncTask(TicketActivity.this, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String output) {


                TextView tv = findViewById(R.id.tv_total_submit);

                TableLayout ll =  findViewById(R.id.tableViewTicket);
                TableRow row1= new TableRow(TicketActivity.this);
                TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                lp1.setMargins(0,0,0,0);
                row1.setLayoutParams(lp1);
                TextView date1 = new TextView(TicketActivity.this);
                TextView qty1 = new TextView(TicketActivity.this);
                date1.setPadding(10, 10,0,10);
                qty1.setPadding(120, 10,0,10);
                date1.setText("Plastic Agent Name /\n Date");
                qty1.setText("Status");
                date1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                qty1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                date1.setTypeface(null, Typeface.BOLD);
                qty1.setTypeface(null, Typeface.BOLD);
                row1.addView(date1);
                row1.addView(qty1);
                row1.setBackgroundResource(R.drawable.row_border);
                ll.addView(row1);

                try {
                    JSONArray jsonArray = new JSONArray(output.trim());
                    //JSONArray jsonArray = jsonObject;
                    //int ticket_length = 0;
                        /*if(jsonArray.length()>10){
                            ticket_length=10;
                        }else{
                            ticket_length = jsonArray.length();
                        }*/

                    for (int i = jsonArray.length()-1; i >= 0; i--) {
                        TableRow row = new TableRow(TicketActivity.this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                        row.setLayoutParams(lp);
                        TextView date = new TextView(TicketActivity.this);
                        TextView qty = new TextView(TicketActivity.this);
                        date.setPadding(10, 10, 0, 10);
                        qty.setPadding(120, 10, 0, 10);

                        JSONObject explrObject = jsonArray.getJSONObject(i);
                        //for data of table :
                        date.setText(explrObject.getString("contact_person_name") + "\n" + explrObject.getString("date"));
                        qty.setText(explrObject.getString("ticket_status"));
                        row.addView(date);
                        row.addView(qty);
                        row.setBackgroundResource(R.drawable.row_border);
                        ll.addView(row);
                    }

                } catch (Exception e) {

                }


            }
        });
        task.execute(getString(R.string.server_path)+"api/get_user_tickets");



        Button home = findViewById(R.id.button40);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(TicketActivity.this);
            }
        });

    }

}
