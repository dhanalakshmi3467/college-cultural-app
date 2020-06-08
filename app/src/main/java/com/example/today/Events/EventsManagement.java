package com.example.today.Events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.Dashboard;
import com.example.today.R;
import com.example.today.adapters.DisplayEventsAdapter;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.ArrayList;
import java.util.List;

//import static com.example.today.backgroundWorker.Urls.GET_EVENTS_URL;
import static com.example.today.backgroundWorker.Urls1.GET_EVENTS_URL;

public class EventsManagement extends AppCompatActivity {

    private RecyclerView eventsRecycleView;
    private TextView button;
    private static String eventType;
    public static ArrayList<Events> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_management);
        Intent intent = getIntent();
        eventType = intent.getStringExtra("type");
        eventsRecycleView = findViewById(R.id.events_recycleView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventsRecycleView.setHasFixedSize(true);
        eventsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        button = findViewById(R.id.createEventByUser);
        loadEvents(eventType);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.ERROR, "CREATE_EVENT_BTN", eventType);
                Intent intent = new Intent(EventsManagement.this, CreateEvent.class);
                intent.putExtra("type", eventType);
                startActivity(intent);
            }
        });


    }

    public void loadEvents(String type) {
        String url = String.format("%s?type=%s&email=%s", GET_EVENTS_URL, type, Dashboard.LoggedInUserInfo.getEmail());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            CollectionType typeReference =
                                    TypeFactory.defaultInstance().constructCollectionType(List.class, Events.class);
                            Log.println(Log.ERROR, "GET_EVENTS", response);
                            List<Events> events = objectMapper.readValue(response, typeReference);
                            DisplayEventsAdapter adapter = new DisplayEventsAdapter(EventsManagement.this, events);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EventsManagement.this, DisplayEventTypes.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
