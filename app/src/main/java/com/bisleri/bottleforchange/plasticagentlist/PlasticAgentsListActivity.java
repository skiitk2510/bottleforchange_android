package com.bisleri.bottleforchange.plasticagentlist;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bisleri.bottleforchange.CallAgentActivity;
import com.bisleri.bottleforchange.DropPinActivity;
import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.Session;
import com.bisleri.bottleforchange.pincodesearch.AgentDetails;
import com.google.gson.Gson;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PlasticAgentsListActivity extends AppCompatActivity {
    private Context context;
    Session session;//global variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastic_agents_list);
        context = this;
        session = Session.getInstance(context); //in oncreate

        HashMap postData = new HashMap();
        postData.put("user_id", session.getUserId());
        PostResponseAsyncTask task = new PostResponseAsyncTask(PlasticAgentsListActivity.this, postData, new AsyncResponse() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void processFinish(String output) {

                //Toast.makeText(PlasticAgentsListActivity.this, output, Toast.LENGTH_LONG).show();
                RadioGroup rgp= findViewById(R.id.paradio2);
                rgp.removeAllViews();

                RadioGroup.LayoutParams rprms;

                try{
                    JSONArray jsonArray = new JSONArray(output.trim());
                    //JSONArray jsonArray = jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //explrObject.getString("name")
                        JSONObject explrObject = jsonArray.getJSONObject(i);
                        if(true){

                            int distSize, contactPersonSize, addrSize;

                            distSize = explrObject.getString("dist").length()+2; //3
                            contactPersonSize = explrObject.getString("contact_person_name").length();//3
                            addrSize = explrObject.getString("address").length();

                            String title = explrObject.getString("dist")+"km - "+explrObject.getString("contact_person_name")+"\n"+explrObject.getString("address")+"\n"+explrObject.getString("mobile_number");

                            RadioButton radioButton = new RadioButton(PlasticAgentsListActivity.this);

                            SpannableString ss = new SpannableString(title);

                            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

                            final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(252, 238, 16));
                            final ForegroundColorSpan fcs_name = new ForegroundColorSpan(Color.rgb(73, 255, 216));

                            ss.setSpan(boldSpan, 0, distSize, 0);
                            ss.setSpan(new RelativeSizeSpan(1.2f), 0,distSize, 0);
                            ss.setSpan(fcs, 0, distSize, 0);
                            int startAddr = distSize+contactPersonSize+3;
                            int endAddr = distSize+contactPersonSize+addrSize+3;
                            ss.setSpan(fcs, startAddr, endAddr, 0);
                            ss.setSpan(fcs_name, distSize+3, startAddr, 0);

                            radioButton.setText(ss);
                            radioButton.setId(View.generateViewId());
                            radioButton.setTag(explrObject.getString("user_id"));
                            radioButton.setTextColor(PlasticAgentsListActivity.this.getResources().getColor(R.color.textcolor));

                            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                            rprms.setMargins(0,0,0,25);
                            rgp.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                            rgp.addView(radioButton, rprms);
                            rgp.setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_textfield, PlasticAgentsListActivity.this.getTheme()));
                        }
                    }

                }catch(Exception e){

                }

            }
        });
        task.execute(getString(R.string.server_path)+"api/get_plastic_agents_distance_list");

        Button reqbtn = findViewById(R.id.button43);

        reqbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RadioGroup radioGroup = findViewById(R.id.paradio2);
                final int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1){
                    Toast.makeText(PlasticAgentsListActivity.this, "Please select plastic agent.", Toast.LENGTH_LONG).show();
                    return ;
                }

                String user_id = session.getUserId();
                HashMap postData = new HashMap();
                postData.put("user_id", user_id);

                RadioButton radioButton; radioButton = findViewById(selectedId);
                final String buttontext = radioButton.getText().toString();
                String toUserId = radioButton.getTag().toString();
                postData.put("to_user_id", toUserId);
                PostResponseAsyncTask task = new PostResponseAsyncTask(PlasticAgentsListActivity.this, postData, new AsyncResponse() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void processFinish(String output) {

                        try{

                            // find the radiobutton by returned id

                            /*String[] splitData = buttontext.split("\n");

                            String mobile_number = splitData[2];

                            Intent intent = new Intent(PlasticAgentsListActivity.this, CallAgentActivity.class);
                            intent.putExtra("text", buttontext);
                            intent.putExtra("mobile_number", mobile_number);
                            startActivity(intent);*/

                            AgentDetails agent = new Gson().fromJson(buttontext, AgentDetails.class);
                            CallAgentActivity.maketIntent(context, agent);

                        }catch(Exception e){
                            Toast.makeText(PlasticAgentsListActivity.this, "Something went wrong kindly try again!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                task.execute(getString(R.string.server_path)+"api/submit_support_ticket");

            }
        });

        Button changepin = findViewById(R.id.button47);

        changepin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PlasticAgentsListActivity.this, DropPinActivity.class);
                startActivity(intent);
            }
        });

    }
}
