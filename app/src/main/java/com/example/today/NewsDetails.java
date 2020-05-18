package com.example.today;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.ecommerce.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.today.Urls.NEWS_DETAILS_URL;

public class NewsDetails extends AppCompatActivity {

    List<NewsList> eventList;

    NewsList event;

    TextView txtId, title, info, date, news_by;

    ImageView imageView;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        loadEventDetails(id);


        txtId = findViewById(R.id.newsId);
        title = findViewById(R.id.newsTitle);
        info = findViewById(R.id.newsDesc);
        news_by = findViewById(R.id.newsby);
        date = findViewById(R.id.newsDate);
        imageView = findViewById(R.id.newsIamge);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadEventDetails(String id) {

        String url = "http://192.168.29.214/newsdetails.php?id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject product = new JSONObject(response);
                            Log.println(Log.INFO, "Event response", product.toString());

                            event = new NewsList(product.getInt("id"),
                                    product.getString("title"),
                                    product.getString("info"),
                                    product.getString("date"),
                                    product.getString("news_by"),
                                    product.getString("Image")
                            );


                            txtId.setText("Event Id: " + String.valueOf(event.getId()));
                            title.setText("title: " + String.valueOf(event.getTitle()));
                            info.setText("info: " + String.valueOf(event.getInfo()));
                            date.setText("date: " + String.valueOf(event.getDate()));
                            news_by.setText("news_by: " + String.valueOf(event.getNews_by()));

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


}

