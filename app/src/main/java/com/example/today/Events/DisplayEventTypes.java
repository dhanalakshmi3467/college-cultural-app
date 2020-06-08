package com.example.today.Events;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.Dashboard;
import com.example.today.R;
import com.example.today.adapters.DisplayEventTypeAdapter;
import com.example.today.models.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.*;

//import static com.example.today.backgroundWorker.Urls.GET_EVENT_TYPE_URL;
import static com.example.today.backgroundWorker.Urls1.GET_EVENT_TYPE_URL;

public class DisplayEventTypes extends AppCompatActivity {
 /*   private FloatingActionButton floatingActionButton;
    public EventType[] eventTypeModel;*/
    private RecyclerView recyclerView;
    private TextView noEventFound;

    Toolbar toolbar2;

    private List<EventType> eventTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noEventFound = findViewById(R.id.myEventNoEventFound);

        recyclerView = findViewById(R.id.eventsType);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      setupToolbar();

       // setupCollapsingToolbar();

//        deleteMe();

        getEventType();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void setupToolbar() {
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
   /* private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }*/

/*
//TODO  Putti Don't remove this code

    private void deleteMe() {
        com.example.today.Events.EventType eventType = new com.example.today.Events.EventType();
        try {
            eventTypeModel = eventType.getEventTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (EventType events : eventTypeModel) {
            Log.println(Log.DEBUG, "GET_EVENT_TYPE", events.getId() + "\t" + events.getType());
        }
    }*/

    private void getEventType() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_EVENT_TYPE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            CollectionType typeReference =
                                    TypeFactory.defaultInstance().constructCollectionType(List.class, EventType.class);
                            List<EventType> eventTypes = objectMapper.readValue(response, typeReference);
                            DisplayEventTypeAdapter adapter = new DisplayEventTypeAdapter(DisplayEventTypes.this, eventTypes);
                            recyclerView.setAdapter(adapter);
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
        Intent intent = new Intent(DisplayEventTypes.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}

