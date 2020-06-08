package com.example.today.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.R;
import com.example.today.adapters.CampusNewsAdapter;
import com.example.today.models.NewsList;

import java.util.List;


import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.example.today.backgroundWorker.Urls1.CAMPUS_NEWS_URL;

//import static com.example.today.backgroundWorker.Urls.CAMPUS_NEWS_URL;


public class CampusNews extends AppCompatActivity implements CampusNewsAdapter.OnItemClicked {

    private static final String URL_PRODUCTS = CAMPUS_NEWS_URL;

    List<NewsList> productList;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.news_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        productList = new ArrayList<>();

        loadProducts();

    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);

                                productList.add(new NewsList(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("news_by"),
                                        product.getString("info"),
                                        product.getString("date"),
                                        product.getString("image")
                                ));
                            }


                            CampusNewsAdapter adapter = new CampusNewsAdapter(CampusNews.this, productList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.println(Log.ERROR, "putti", error.getMessage());
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemClick(int position) {

    }
}




























