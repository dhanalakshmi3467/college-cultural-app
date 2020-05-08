package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.today.Urls.ANOTHER1_URL;
import static com.example.today.Urls.ANOTHER_URL;

public class AnotherActivity1 extends AppCompatActivity {

    List<ListModel2> eventList;

    ListModel2 event;

    TextView eventId, title, desc, price;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        loadEventDetails(id);
        eventId = findViewById(R.id.eventId);
        title = findViewById(R.id.eventTitle);
        desc = findViewById(R.id.eventDesc);
        price = findViewById(R.id.eventPricing);

        imageView = findViewById(R.id.eventImage);
    }

    public void loadEventDetails(String id) {
        String url = ANOTHER1_URL + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject product = new JSONObject(response);
                            Log.println(Log.INFO, "Event response", product.toString());

                            event = new ListModel2(product.getInt("id"),
                                    product.getString("title"),
                                    product.getString("shortdesc"),

                                    product.getDouble("price"),
                                    product.getString("image"));


                            eventId.setText("Event Id: " + String.valueOf(event.getId()));
                            title.setText("title: " + String.valueOf(event.getTitle()));
                            desc.setText("desc: " + String.valueOf(event.getShortdesc()));
                            price.setText("price: " + String.valueOf(event.getPrice()));

                            try {
//                                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(event.getImage()).getContent());
                                Picasso.get()
                                        .load(event.getImage())
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

}
