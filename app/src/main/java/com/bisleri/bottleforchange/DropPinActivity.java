package com.bisleri.bottleforchange;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bisleri.bottleforchange.plasticagentlist.PlasticAgentsListActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DropPinActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONArray jsonArray;
    private String clickedLat;
    private String clickedLog;
    private String user_id;
    private String is_pin_set_local="0";
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_apin);

        session = Session.getInstance(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        user_id =session.getUserId();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        HashMap postData = new HashMap();
        postData.put("user_id", "0");
        PostResponseAsyncTask task = new PostResponseAsyncTask(DropPinActivity.this, postData, new AsyncResponse() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void processFinish(String output) {

                try{
                    jsonArray = new JSONArray(output.trim());
                    setMarkers();
                    JSONObject explrObject = jsonArray.getJSONObject(0);
                    String lat =explrObject.getString("lat");
                    String lng =explrObject.getString("long");
                    String name =explrObject.getString("name");
                    LatLng p2 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p2, 13.0f));
                    //mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);

                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        task.execute(getString(R.string.server_path)+"api/get_all_pa");

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){
                JSONObject explrObject = null;
                try {
                    mMap.clear();
                    setMarkers();
                    mMap.addMarker(new MarkerOptions().position(new LatLng( point.latitude, point.longitude)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    clickedLat = String.valueOf(point.latitude);
                    clickedLog = String.valueOf(point.longitude);
                    is_pin_set_local = "1";
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        Button savePin = (Button)findViewById(R.id.button42);
        savePin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(!is_pin_set_local.matches("1")){
                    Toast.makeText(DropPinActivity.this, "Please mark your current location", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap postData = new HashMap();
                postData.put("user_id", user_id);
                postData.put("lat", clickedLat);
                postData.put("long", clickedLog);
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(DropPinActivity.this, postData, new AsyncResponse() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void processFinish(String output) {
                        //Toast.makeText(DropAPinActivity.this, output, Toast.LENGTH_LONG).show();
                        session.setPin("1");
                        Intent intent = new Intent(DropPinActivity.this, PlasticAgentsListActivity.class);
                        startActivity(intent);
                    }
                });
                task2.execute(getString(R.string.server_path)+"api/save_drop_pin");

            }
        });


    }

    public void setMarkers(){
        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                String lat =explrObject.getString("lat");
                String lng =explrObject.getString("long");
                String name =explrObject.getString("name");
                String address =explrObject.getString("address");
                //String lat = "19.385228", lng="72.833063";
                LatLng p2 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                mMap.addMarker(new MarkerOptions().position(p2).title(name).snippet(address)).showInfoWindow();
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p2, 16.0f));
            }

        }catch (Exception e){

        }
    }
}
