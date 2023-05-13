package com.bisleri.bottleforchange.submittedplastics;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.TableLayoutBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bisleri.bottleforchange.R;
import com.bisleri.bottleforchange.Session;
import com.bisleri.bottleforchange.AuthActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.bisleri.bottleforchange.async.AsyncResponse;
import com.bisleri.bottleforchange.async.PostResponseAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SubmittedPlasticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_plastic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Session session; //global variable
        session = Session.getInstance(SubmittedPlasticActivity.this); //in oncreate
        String user_id = session.getUserId();

        String myLovelyVariable;

        final TextView tvTotal = findViewById(R.id.tv_total_submit);
        tvTotal.setGravity(Gravity.CENTER_VERTICAL);
        HashMap postData2 = new HashMap();
        postData2.put("user_id", user_id);
        Log.d("DOWNLOAD K", "onCreate: " + postData2);
        PostResponseAsyncTask task = new PostResponseAsyncTask(SubmittedPlasticActivity.this, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    SubmittedPlasticResponse response = new Gson().fromJson(output,SubmittedPlasticResponse.class);
                    showTableLayout(response.content, user_id);
                    tvTotal.setText("Submitted Plastic : " + response.total);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }
        });
        task.execute(getString(R.string.server_path)+"api/get_plastic_submitted");

        Button sphome = findViewById(R.id.sphome);

        sphome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthActivity.goHomeIntent(SubmittedPlasticActivity.this);
            }
        });
    }

    public void showTableLayout(ArrayList<SubmittedContent> res, String user_id) {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);  //Table layout
        TableRow tbrow0 = new TableRow(this);//Table row for headers

        //Table Headers
        TextView tv0 = new TextView(this);
        tv0.setText("Date            ");
        tv0.setTextSize(17);
        tv0.setTextColor(Color.WHITE);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10, 10,10,10);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText("Name         ");
        tv1.setTextSize(17);
        tv1.setTextColor(Color.WHITE);
        tv1.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10, 10,10,10);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("Quantity         ");
        tv2.setTextSize(17);
        tv2.setTextColor(Color.WHITE);
        tv2.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10, 10,10,10);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("Receipt  ");
        tv3.setTextSize(17);
        tv3.setTextColor(Color.WHITE);
        tv3.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10, 10,10,10);
        tbrow0.addView(tv3);

        //End of Table Headers
        //Add to the tablelayout
        tbrow0.setBackgroundResource(R.drawable.row_border);
        stk.addView(tbrow0);

        //Below is the Table data with 4 columns
        for (int i = 0; i < res.size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            TextView t1v = new TextView(this);
            t1v.setText(res.get(i).date);
            t1v.setTag(res.get(i).date);
            t1v.setPadding(10, 10, 10, 10);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(res.get(i).name_of_entity);
            t1v.setPadding(10, 10, 10, 10);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(res.get(i).total_per_day);
            t1v.setPadding(10, 10, 10, 10);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);

            ImageView view = new ImageView(this);
            //view.setImageResource(R.drawable.download);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.download); //image is your image
            Bitmap mp = Bitmap.createScaledBitmap(bmp, 30, 80, true);
            view.setImageBitmap(mp);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setPadding(10, 10, 10, 10);
            tbrow.addView(view);

            tbrow.setBackgroundResource(R.drawable.row_border);
            view.setOnClickListener(new ImageView.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    download((String)t1v.getTag(), user_id);
                }
            });
            stk.addView(tbrow);
        }
    }

    public void download(String date, String user_id) {
        Log.d("DATE DOWNLOAD", "download: "+ date);
        Uri url = Uri.parse(getString(R.string.server_path)+"api/download_receipt?collection_date="+date+"&user_id="+user_id);
        String filename = "receipt";
        DownloadManager.Request request = new DownloadManager.Request(url);
        request.setMimeType("application/pdf");
        request.setAllowedOverMetered(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Log.d("405", "download: ");
        dm.enqueue(request);
    }
}
