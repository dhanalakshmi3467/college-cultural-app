package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AnotherActivity extends AppCompatActivity {

    List<ListModel2> eventList;

    ListModel2 event;

    TextView eventId, title, desc , price;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        loadEventDetails(id);
        eventId = findViewById(R.id.eventId);
        title = findViewById(R.id.eventTitle);
        desc = findViewById(R.id.eventDesc);
        price = findViewById(R.id.eventPricing);

        imageView = findViewById(R.id.eventImage);
    }

    public void loadEventDetails(String id)  {

        String url = "http://192.168.43.214/displayitems.php?id=" + id;
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


                            eventId.setText("Event Id: "+String.valueOf( event.getId()));
                            title.setText("title: "+String.valueOf(event.getTitle()));
                            desc.setText("desc: "+String.valueOf(event.getShortdesc()));
                            price.setText("price: "+String.valueOf(event.getPrice()));

                            try{
//                                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(event.getImage()).getContent());
                                Picasso.get()
                                        .load(event.getImage())
                                        .into(imageView);
//                                imageView.setImageBitmap(bitmap);
                            }catch (Exception e){
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
                        Log.println(Log.ERROR,"VolleyError", error.toString());
                    }
                });


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void ticket(View view) {

    }
}
