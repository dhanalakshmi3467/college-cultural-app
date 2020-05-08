package com.example.today;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.models.EventType;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;

import static com.example.today.Urls.GET_EVENTS_URL;
import static com.example.today.Urls.GET_EVENT_TYPE_URL;

public class EventsManagement extends AppCompatActivity {

    private RecyclerView eventsRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_management);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        eventsRecycleView = findViewById(R.id.events_recycleView);
        eventsRecycleView.setHasFixedSize(true);
        eventsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        loadEvents(id);
    }

    public void loadEvents(String id) {

        String url = GET_EVENTS_URL + id;
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
                            DisplayEvents adapter = new DisplayEvents(EventsManagement.this, events);
                            eventsRecycleView.setAdapter(adapter);
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
