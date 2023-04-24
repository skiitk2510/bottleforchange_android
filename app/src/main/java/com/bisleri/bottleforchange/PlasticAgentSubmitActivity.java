package com.bisleri.bottleforchange;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class PlasticAgentSubmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastic_agent_submit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button search = findViewById(R.id.button21);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String user_id = Session.getInstance(PlasticAgentSubmitActivity.this).getUserId();

                TextView phone_number_tv = findViewById(R.id.editText11);
                String phone_number = phone_number_tv.getText().toString();
                HashMap postData = new HashMap();
                postData.put("mobile_number", phone_number);
                postData.put("user_id", user_id);
                PostResponseAsyncTask task = new PostResponseAsyncTask(PlasticAgentSubmitActivity.this, postData, new AsyncResponse() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void processFinish(String output) {

                        RadioGroup rgp=  findViewById(R.id.radiogroup);
                        rgp.removeAllViews();

                        RadioGroup.LayoutParams rprms;
                        try{
                            JSONArray jsonArray = new JSONArray(output.trim());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject explrObject = jsonArray.getJSONObject(i);

                                String category_id= getIntent().getStringExtra("category_id");

                                String[] category_id_array = category_id.split(",");

                                if(Arrays.asList(category_id_array).contains(explrObject.getString("category_id"))){

                                    RadioButton radioButton = new RadioButton(PlasticAgentSubmitActivity.this);
                                    radioButton.setText(
                                                "Entity Name : "+explrObject.getString("name_of_entity") + "\n"
                                            +   "Address :  "+explrObject.getString("address") + "\n"
                                            +   "City :  "+explrObject.getString("city") + "\n"
                                            +   "Pincode : "+explrObject.getString("pincode") + "\n"
                                            +   "Contact Person : "+explrObject.getString("contact_person_name") + "\n"
                                            +   "Mobile No: "+explrObject.getString("mobile_number"));
                                    radioButton.setId(View.generateViewId());
                                    radioButton.setTextColor(PlasticAgentSubmitActivity.this.getResources().getColor(R.color.textcolor));
                                    radioButton.setTag(explrObject.getString("user_id"));

                                    rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                                    rprms.setMargins(0,0,0,25);
                                    rgp.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                                    rgp.addView(radioButton, rprms);
                                    rgp.setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_textfield, PlasticAgentSubmitActivity.this.getTheme()));
                                }
                            }

                        }catch(Exception e){

                        }
                    }
                });
                task.execute(getString(R.string.server_path)+"api/get_reg_users");

            }
        });

        Button submit = findViewById(R.id.button22);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String user_id = Session.getInstance(PlasticAgentSubmitActivity.this).getUserId();

                EditText phone_number_tv = findViewById(R.id.editText11);
                EditText hard_plastic_kg_et = findViewById(R.id.editText13);
                EditText hard_plastic_gm_et = findViewById(R.id.editText14);
                EditText soft_plastic_kg_et = findViewById(R.id.editText15);
                EditText soft_plastic_gm_et = findViewById(R.id.editText16);

                String hard_plastic_kg = hard_plastic_kg_et.getText().toString();
                String hard_plastic_gm = hard_plastic_gm_et.getText().toString();
                String soft_plastic_kg = soft_plastic_kg_et.getText().toString();
                String soft_plastic_gm = soft_plastic_gm_et.getText().toString();

                if(hard_plastic_kg.matches("") && soft_plastic_kg.matches("") && hard_plastic_gm.matches("") && soft_plastic_gm.matches("")){
                    Toast.makeText(PlasticAgentSubmitActivity.this, "Please enter plastic quantity", Toast.LENGTH_LONG).show();
                    return ;
                }


                if(hard_plastic_kg.matches("")){
                    hard_plastic_kg = "0";
                }

                if(hard_plastic_gm.matches("")){
                    hard_plastic_gm = "0";
                }

                if(soft_plastic_kg.matches("")){
                    soft_plastic_kg = "0";
                }

                if(soft_plastic_gm.matches("")){
                    soft_plastic_gm = "0";
                }




                String phone_number = phone_number_tv.getText().toString();
                HashMap postData = new HashMap();
                postData.put("phone_number", phone_number);
                postData.put("save", "1");
                postData.put("hard_plastic_kg", hard_plastic_kg);
                postData.put("hard_plastic_gm", hard_plastic_gm);
                postData.put("soft_plastic_kg", soft_plastic_kg);
                postData.put("soft_plastic_gm", soft_plastic_gm);
                postData.put("user_id", user_id);
                //postData.put("selected_user", phone_number);
                RadioGroup radioGroup = findViewById(R.id.radiogroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1){
                    Toast.makeText(PlasticAgentSubmitActivity.this, "Please select end user.", Toast.LENGTH_LONG).show();
                    return ;
                }

                // find the radiobutton by returned id
                RadioButton radioButton; radioButton = findViewById(selectedId);
                
                String user_ticket_id = radioButton.getTag().toString();

                String[] splitData = user_ticket_id.split("/");

                postData.put("given_by_user_id", splitData[0]);

                String ticket_id = "0";
                if(splitData.length==2){
                    ticket_id = splitData[1];
                }

                postData.put("ticket_id", ticket_id);

                //Toast.makeText(PlasticAgentSubmitActivity.this,radioButton.getTag().toString(), Toast.LENGTH_SHORT).show();
                PostResponseAsyncTask task3 = new PostResponseAsyncTask(PlasticAgentSubmitActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        if(output.matches("false"))
                            Toast.makeText(PlasticAgentSubmitActivity.this, output, Toast.LENGTH_LONG).show();
                        else{
                            Intent i = new Intent(PlasticAgentSubmitActivity.this, SuccessActivity.class);
                            startActivity(i);
                        }
                    }
                });
                task3.execute(getString(R.string.server_path)+"api/submit_plastic_agent");

            }
        });

    }

}
