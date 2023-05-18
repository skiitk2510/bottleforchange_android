package com.bisleri.bottleforchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.layout.simple_spinner_dropdown_item;

public class PlasticAgentAggregatorReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastic_agent_aggregator_reg);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<String> cList=new ArrayList<>();
        try {
            if (true) {
                cList.add(String.valueOf(new StringWithTag("Plastic Agent", "Plastic Agent")));
                cList.add(String.valueOf(new StringWithTag("Aggregator", "Aggregator")));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        Spinner dropdown = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(PlasticAgentAggregatorReg.this, simple_spinner_dropdown_item, cList);
        dropdown.setAdapter(adapter);

        final EditText name_housing_society = findViewById(R.id.editText7);

        final EditText mobileNumber = findViewById(R.id.editText2);
        final EditText altNumber = findViewById(R.id.editText3);
        final EditText address = findViewById(R.id.editText4);
        final EditText pincode = findViewById(R.id.editText5);
        final EditText email_id = findViewById(R.id.editText6);
        final EditText pan = findViewById(R.id.pan);
        final EditText registration = findViewById(R.id.registration);
        final EditText gst = findViewById(R.id.gst_no);
        final Spinner relation = findViewById(R.id.spinner);
        final EditText etCity = findViewById(R.id.et_city);
        final Spinner state = findViewById(R.id.spinner_state);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.state));
        state.setAdapter(spinnerCountShoesArrayAdapter);

        final TextView errorTextView = findViewById(R.id.errorTextView);

        Button submit = findViewById(R.id.button9);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //VALIDATION OF FIELDS :
                String name_value = name_housing_society.getText().toString();
                String mobileNumber_value = mobileNumber.getText().toString();
                String altNumber_value = altNumber.getText().toString();
                String address_value = address.getText().toString();
                String pincode_value = pincode.getText().toString();
                String pan_value = pan.getText().toString();
                String registration_value = registration.getText().toString();
                String gst_value = gst.getText().toString();
                String email_id_value = email_id.getText().toString();
                String name_housing_society_value ="";// name_housing_society.getText().toString();
                String no_of_households_value = "0";//no_of_households.getText().toString();
                final String relation_value = relation.getSelectedItem().toString();
                String city_value = etCity.getText().toString();
                String state_value = state.getSelectedItem().toString();
                String emptyErrorStr = "";
                String errorStr = "";


                ArrayList<String> emptyErrorArray  = new ArrayList<>();
                ArrayList<String> otherErrorArray  = new ArrayList<>();

                if(name_value.matches("")){
                    emptyErrorArray.add("Contact Person Name");
                }

                if(relation.getSelectedItemPosition()==0){
                    emptyErrorArray.add("Relation");
                }

                if(mobileNumber_value.matches("") ){
                    emptyErrorArray.add("Mobile Number");
                }else{
                    if(!TextUtils.isDigitsOnly(mobileNumber_value) || mobileNumber_value.length()!=10){
                        otherErrorArray.add("Mobile number should be 10 digits only");
                    }
                }

                if(!altNumber_value.matches("") && (!TextUtils.isDigitsOnly(altNumber_value) || altNumber_value.length()!=10)){
                    otherErrorArray.add("Alt Mobile number should be 10 digits only");
                }

                if(address_value.matches("")){
                    emptyErrorArray.add("Address");
                }
                if(state.getSelectedItemPosition()==0){
                    emptyErrorArray.add("State");
                }
                if(city_value.matches("")){
                    emptyErrorArray.add("City");
                }
                if(pincode_value.matches("")){
                    emptyErrorArray.add("Pincode");
                }else{
                    if(!TextUtils.isDigitsOnly(pincode_value) || pincode_value.length()!=6){
                        otherErrorArray.add("Entered pincode should 6 digits only");
                    }
                }

                if(email_id_value.matches("") && !Patterns.EMAIL_ADDRESS.matcher(email_id_value).matches()){
                    otherErrorArray.add("Enter valid email id");
                }

                CheckBox checkBox = findViewById(R.id.checkBox);
                if(!checkBox.isChecked()){
                    otherErrorArray.add("Please refer terms and conditions");
                }

                if(emptyErrorArray!=null){
                    emptyErrorStr = android.text.TextUtils.join(", ", emptyErrorArray);
                }

                if(emptyErrorStr!="" ){
                    errorStr = "Please fill "+emptyErrorStr+" fields!\n";
                }

                if(otherErrorArray!=null){
                    errorStr += android.text.TextUtils.join("\n ", otherErrorArray);
                }

                errorTextView.setText(errorStr);

                if(errorStr.trim()!=""){
                    return ;
                }

                HashMap postData = new HashMap();
                postData.put("app_entity_name", name_housing_society_value);
                postData.put("app_no_of_households", no_of_households_value);
                postData.put("app_relation", relation_value);
                postData.put("app_contact_name", name_value);
                postData.put("app_mobile_number", mobileNumber_value);
                postData.put("app_alt_number", altNumber_value);
                postData.put("app_address", address_value);
                postData.put("app_pincode", pincode_value);
                postData.put("app_email_address", email_id_value);
                postData.put("state", state_value);
                postData.put("city", city_value);
                postData.put("app_pan_no", pan_value);
                postData.put("app_registration_no", registration_value);
                postData.put("app_gst_no", gst_value);

                final PostResponseAsyncTask task = new PostResponseAsyncTask( v.getContext(), postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        //Toast.makeText(housingSocietyActivity.this, output, Toast.LENGTH_LONG).show();
                        Session session = Session.getInstance(PlasticAgentAggregatorReg.this); //in oncreate
                        //and now we set sharedpreference then use this like
                        if(!output.equals("exists")){
                            session.setUserId(output);
                            session.setPin("0");

                            if(relation_value.matches("Plastic Agent"))
                                session.setCategoryId("6");
                            else
                                session.setCategoryId("7");

                            Intent i = new Intent(PlasticAgentAggregatorReg.this, OtpActivity.class);
                            i.putExtra("mobile_number", mobileNumber.getText().toString());
                            startActivity(i);
                        }
                        else{

                            Toast.makeText(PlasticAgentAggregatorReg.this, "Already registered!", Toast.LENGTH_LONG).show();

                        }


                    }
                });
                task.execute(getString(R.string.server_path)+"api/insert_pa_agg_data");
            }
        });

        //Terms And Condition Snippet Starts
        Button tncButton = findViewById(R.id.tncButton);
        tncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlasticAgentAggregatorReg.this);
                builder.setMessage(Html.fromHtml(getString(R.string.terms_conditions))).setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //Terms And Condition Snippet Ends
    }

}
