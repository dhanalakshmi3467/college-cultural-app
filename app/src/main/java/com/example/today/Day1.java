package com.example.today;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.ecommerce.Product;

import java.util.List;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.example.today.Urls.DAY1_URL;

/**
 * Created by @vitovalov on 30/9/15.
 */


public class Day1 extends AppCompatActivity implements ListaAdapter.OnItemClicked {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = DAY1_URL;

    //a list to store all the products
    List<ListModel2> productList;

    //the recyclerview
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.techtix);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new ListModel2(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("shortdesc"),

                                        product.getDouble("price"),
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
//                            ProductsAdapter adapter = new ProductsAdapter(MainActivity.this, productList);
                            ListaAdapter adapter = new ListaAdapter(Day1.this, productList);
                            recyclerView.setAdapter(adapter);
//                            adapter.setOnClick(Day1.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
// Toast.makeText(getApplicationContext(),"hello world", Toast.LENGTH_LONG);

                        //   Log.println(Log.ERROR, "putti", error.getMessage());
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


   /* @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }
   */

    @Override
    public void onItemClick(int position) {

    }
}





























   /* private ListAdapter2 mAdapter;

    private String mItemData = "Robotics  Coding  Gaming  Online  Quizzard  Business  Solve It  Miscellaneous";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.techtix, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.fragment_list_rv2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        String[] listItems = mItemData.split("  ");

        List<String> list = new ArrayList<String>();
        Collections.addAll(list, listItems);

        mAdapter = new ListAdapter2(list);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}*/
