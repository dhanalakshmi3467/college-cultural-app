package com.example.today;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


import static com.example.today.Urls.SCHEDULE1_URL;


public class ScheduleActivity extends AppCompatActivity {

    List<ListData1> eventList;

    ListData1 event;

    TextView eventId, title, desc, time,contactus;
    private String eventName;



    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NestedScrollView parentView;


    ImageView imageView;
    Toolbar toolbar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(eventName);

//        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        FloatingActionButton toolbarFab = (FloatingActionButton) findViewById(R.id.schedule_details_fab);

        parentView = (NestedScrollView) findViewById(R.id.schedule_details_nested_scroll_view);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        loadEventDetails(id);
        eventId = findViewById(R.id.eventId);
        title = findViewById(R.id.eventTitle);
        desc= findViewById(R.id.eventDescription);
        time = findViewById(R.id.eventTime);

        contactus = findViewById(R.id.contactus);




    }


    public void loadEventDetails(String id) {

        String url = SCHEDULE1_URL + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject product = new JSONObject(response);
                            Log.println(Log.INFO, "Event response", product.toString());

                            event = new ListData1(product.getInt("id"),
                                    product.getString("title"),
                                    product.getString("shortdesc"),

                                    product.getString("time"),
                                    product.getString("contactus"));

//                            eventId.setText("Event Id: " + String.valueOf(event.getId()));
                            title.setText("title: " + String.valueOf(event.getTitle()));
                            desc.setText("description: " + String.valueOf(event.getShortdesc()));
                            time.setText("Date and Time: " + String.valueOf(event.getTime()));
                            contactus.setText("mobileno: " + String.valueOf(event.getContactus()));

                            try {
//                                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(event.getImage()).getContent());
                                Picasso.get()
                                        .load(event.getTitle())
                                        .into(imageView);
//                                imageView.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "VolleyError", error.toString());
                    }
                });


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void ticket(View view) {

    }
}
