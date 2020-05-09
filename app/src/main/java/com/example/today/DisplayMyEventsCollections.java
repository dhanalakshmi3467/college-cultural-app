package com.example.today;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;

import static com.example.today.Urls.MY_EVENTS_REGISTERED_URL;

public class DisplayMyEventsCollections extends AppCompatActivity {
    private RecyclerView eventsRecycleView;
    private TextView noEventFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_events_collections);

        noEventFound = findViewById(R.id.myEventNoEventFound);

        eventsRecycleView = findViewById(R.id.events_recycleView);
        eventsRecycleView.setHasFixedSize(true);
        eventsRecycleView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents(MainActivity.LoggedInUserInfo.getEmail());
    }
    public void loadEvents(String email) {

        String url = MY_EVENTS_REGISTERED_URL+ email;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            CollectionType typeReference =
                                    TypeFactory.defaultInstance().constructCollectionType(List.class, Events.class);
                            Log.println(Log.ERROR,"GET_EVENTS", response);
                            List<Events> events = objectMapper.readValue(response, typeReference);
                            if (events.size() == 0){
                                noEventFound.setVisibility(View.VISIBLE);
                            }else{
                                noEventFound.setVisibility(View.GONE);
                                DisplayMyEvents adapter = new DisplayMyEvents(DisplayMyEventsCollections.this, events);
                                eventsRecycleView.setAdapter(adapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GET_EVENT_TYPE", error.getMessage());
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
