package com.example.today;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.adapters.DisplayEventTypeAdapter;
import com.example.today.models.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

import static com.example.today.Urls.GET_EVENT_TYPE_URL;

public class DisplayEventTypes extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar2;
    public EventType[] eventTypeModel;
    private RecyclerView recyclerView;

    private List<EventType> eventTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Button createEvent = findViewById(R.id.createEvent);
/*
        floatingActionButton = findViewById(R.id.createEventFloat);
        if (LoginBackground.admin) {
//            createEvent.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (LoginBackground.admin) {
                    startActivity(new Intent(getApplicationContext(), CreateEvent.class));
                }
            }
        });*/

        recyclerView = findViewById(R.id.eventsType);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        setupToolbar();

//        setupViewPager();

        setupCollapsingToolbar();

//        deleteMe();

        getEventType();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }
/*
    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }*/

    private void setupToolbar() {
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }*/

/*
    public void culture(View view) {
        Intent intent = new Intent(DisplayEventTypes.this, Day1.class);
        startActivity(intent);
    }

    public void tech(View view) {
        Intent intent = new Intent(DisplayEventTypes.this, Day3.class);
        startActivity(intent);
    }

    public void sport(View view) {
        Intent intent = new Intent(DisplayEventTypes.this, Day2.class);
        startActivity(intent);
    }
*/


    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
/*
//TODO  Putti Don't remove this code

    private void deleteMe() {
        com.example.today.EventType eventType = new com.example.today.EventType();
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

