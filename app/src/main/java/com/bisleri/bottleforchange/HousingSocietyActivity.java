package com.bisleri.bottleforchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static android.R.layout.*;

public class HousingSocietyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing_society);

        HashMap postData = new HashMap();
        postData.put("id", "value");
        //You pass postData as the 2nd argument of the constructor
        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(HousingSocietyActivity.this, postData,
                new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        JSONArray jsonArray = null;
                        ArrayList<String> cList=new ArrayList<String>();
                        try {
                            String json = new String(output);
                            jsonArray = new JSONArray(json);
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    cList.add(String.valueOf(new StringWithTag(jsonArray.getJSONObject(i).getString("value"), jsonArray.getJSONObject(i).getString("id"))));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Spinner dropdown = findViewById(R.id.spinner);
                        ArrayAdapter adapter = new ArrayAdapter(HousingSocietyActivity.this, simple_spinner_dropdown_item, cList);
                        dropdown.setAdapter(adapter);
                    }
                });
        loginTask.execute(getString(R.string.server_path)+"api/get-housing-category-relation");

        final EditText name_housing_society = findViewById(R.id.editText7);
        final EditText no_of_households = findViewById(R.id.editText8);

        final EditText contact_person_name = findViewById(R.id.editText9); // name of contact person
        final EditText mobileNumber = findViewById(R.id.editText2);
        final EditText altNumber = findViewById(R.id.editText3);
        final EditText address = findViewById(R.id.editText4);
        final EditText pincode = findViewById(R.id.editText5);
        final EditText email_id = findViewById(R.id.editText6);
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
                String name_value = contact_person_name.getText().toString();
                String mobileNumber_value = mobileNumber.getText().toString();
                String altNumber_value = altNumber.getText().toString();
                String address_value = address.getText().toString();
                String pincode_value = pincode.getText().toString();
                String email_id_value = email_id.getText().toString();
                String name_housing_society_value = name_housing_society.getText().toString();
                String no_of_households_value = no_of_households.getText().toString();
                String relation_value = relation.getSelectedItem().toString();
                String city_value = etCity.getText().toString();
                String state_value = state.getSelectedItem().toString();
                String emptyErrorStr = "";
                String errorStr = "";


                ArrayList<String> emptyErrorArray  = new ArrayList<>();
                ArrayList<String> otherErrorArray  = new ArrayList<>();

                if(name_housing_society_value.matches("")){
                    emptyErrorArray.add("Name of Housing Society");
                }

                if(no_of_households_value.matches("")){
                    emptyErrorArray.add("No. of Households");
                }

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
                    emptyErrorArray.add("Enter valid email id");
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
                postData.put("state", state_value);
                postData.put("city", city_value);
                postData.put("app_alt_number", altNumber_value);
                postData.put("app_address", address_value);
                postData.put("app_pincode", pincode_value);
                postData.put("app_email_address", email_id_value);

                final PostResponseAsyncTask task = new PostResponseAsyncTask( v.getContext(), postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        //Toast.makeText(housingSocietyActivity.this, output, Toast.LENGTH_LONG).show();
                        Session session = Session.getInstance(HousingSocietyActivity.this); //in oncreate
                        //and now we set sharedpreference then use this like
                        if(!output.equals("exists")){
                            session.setUserId(output);
                            session.setCategoryId("5");
                            session.setPin("0");
                            Intent i = new Intent(HousingSocietyActivity.this, OtpActivity.class);
                            i.putExtra("mobile_number", mobileNumber.getText().toString());
                            startActivity(i);
                        }
                        else{

                            Toast.makeText(HousingSocietyActivity.this, "Already registered!", Toast.LENGTH_LONG).show();

                        }


                    }
                });
                task.execute(getString(R.string.server_path)+"api/insert_housing_data");//http://mycrmserver.com/bottle-for-change/public/
            }
        });

        //Terms And Condition Snippet Starts
        Button tncButton = findViewById(R.id.tncButton);
        tncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HousingSocietyActivity.this);
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

