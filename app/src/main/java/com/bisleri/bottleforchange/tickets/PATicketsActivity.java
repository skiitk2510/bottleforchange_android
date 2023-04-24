package com.bisleri.bottleforchange.tickets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.Session;
import com.bisleri.bottleforchange.SubmitPlasticSingleActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class PATicketsActivity extends AppCompatActivity {

    private TicketsListAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patickets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = PATicketsActivity.this;
        RecyclerView rcvTicketsList = findViewById(R.id.rcv_ticket_list);
        rcvTicketsList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvTicketsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvTicketsList.setItemAnimator(new DefaultItemAnimator());
        adapter = new TicketsListAdapter();
        rcvTicketsList.setAdapter(adapter);

        callListApi();

        Button collectplastic = (Button)findViewById(R.id.button35);

        collectplastic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    if(adapter.getSelectedAgent()==null){
                        Toast.makeText(PATicketsActivity.this, "Please select a ticket", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent intent = new Intent(PATicketsActivity.this, SubmitPlasticSingleActivity.class);
                    intent.putExtra(SubmitPlasticSingleActivity.SELECTED_TICKET_DETAILS, adapter.getSelectedAgent());
                    startActivity(intent);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button cancelTicket = findViewById(R.id.button52);

        cancelTicket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCancelTicketDialog();
            }
        });

    }

    private void showCancelTicketDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_two_button_with_input);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!((Activity) context).isDestroyed()) {
                dialog.show();
            }
        }

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_dialog_message);
        TextView tvPositive = (TextView) dialog.findViewById(R.id.tv_dialog_positive);
        TextView tvNegative = (TextView) dialog.findViewById(R.id.tv_dialog_negative);

        tvTitle.setText("Cancel Ticket!");
        tvMessage.setText("Please enter reason for cancellation:");
        tvPositive.setText("Yes");
        tvNegative.setText("No");

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etInput = (EditText) dialog.findViewById(R.id.et_dialog_reason);
                String reason = etInput.getText().toString();
                //Do stuff, possibly set wantToCloseDialog to true then...
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                if (reason.matches("")) {
                    Toast.makeText(PATicketsActivity.this, "Please enter reason!", Toast.LENGTH_LONG).show();
                }else{
                    callCancelApi(reason);
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        tvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    private void callCancelApi(String reason) {
        HashMap postData2 = new HashMap();
        postData2.put("ticket_id", adapter.getSelectedAgent().support_ticket_id);
        postData2.put("delete", "1");
        postData2.put("cancel_reason", reason);
        postData2.put("user_id", Session.getInstance(PATicketsActivity.this).getUserId());
        PostResponseAsyncTask task = new PostResponseAsyncTask(PATicketsActivity.this, postData2, new AsyncResponse() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void processFinish(String output) {
                if (output.matches("success")) {
                    adapter.removeSelectedAgent();
                    Toast.makeText(PATicketsActivity.this, "Ticket cancelled successfully!.", Toast.LENGTH_LONG).show();
                } else {
                    PATicketsActivity.this.finish();
                    Toast.makeText(PATicketsActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });
        task.execute(getString(R.string.server_path) + "api/deletesupportticket");
    }

    private void callListApi() {
        HashMap postData2 = new HashMap();
        postData2.put("user_id",  Session.getInstance(this).getUserId());
        PostResponseAsyncTask task = new PostResponseAsyncTask(PATicketsActivity.this, postData2, new AsyncResponse() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void processFinish(String output) {
                try {
                    TypeToken<ArrayList<TicketDetails>> token = new TypeToken<ArrayList<TicketDetails>>(){};
                    ArrayList<TicketDetails> list = new Gson().fromJson(output, token.getType());
                    adapter.setAdapterList(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute(getString(R.string.server_path)+"api/get_open_tickets");
    }

}
