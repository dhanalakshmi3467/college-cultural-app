package com.example.today;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.today.Events.DisplayEventTypes;
import com.example.today.MyEvents.DisplayMyEventsCollections;
import com.example.today.activities.AboutUs;
import com.example.today.activities.CampusNews;
import com.example.today.activities.Login;
import com.example.today.activities.Sponsors;
import com.example.today.adapters.FeaturedAdapter;
import com.example.today.adapters.MostViewedAdapter;
import com.example.today.models.EventType;
import com.example.today.models.FeaturedHelperClass;
import com.example.today.models.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.today.backgroundWorker.Urls1.GET_DATA_URL;

//import static com.example.today.backgroundWorker.Urls.GET_DATA_URL;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<FeaturedHelperClass> FeaturedData;
    private RecyclerView featuredRecycler, mostViewedRecycler;
    RecyclerView.Adapter adapter;

    long backPressedTime = 0;
    private ObjectMapper objectMapper = new ObjectMapper();
    public static LoginResponse LoggedInUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        featuredRecycler = findViewById(R.id.featured_recycle);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        FeaturedData = new ArrayList<>();
        featuredRecycler();
        mostViewedRecycler();
        setSupportActionBar(toolbar);



        Intent intent = getIntent();
        if (intent.hasExtra("loginResponse")) {
            String loginResponse = intent.getStringExtra("loginResponse");
            try {
                LoggedInUserInfo = objectMapper.readValue(loginResponse, LoginResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //handling floating action menu
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<EventType.MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new EventType.MostViewedHelperClass(R.drawable.laser, "Laser Tag","Test your skills in the real world with lasers and limited lives."));
        mostViewedLocations.add(new EventType.MostViewedHelperClass(R.drawable.dance, "Inter Department Dance","The was perfect dance competitation and blend of dance as well as competation"));
        mostViewedLocations.add(new EventType.MostViewedHelperClass(R.drawable.lan, "Lan Gaming","Prove Your worth against the best  oppostions as you face."));
        mostViewedLocations.add(new EventType.MostViewedHelperClass(R.drawable.chainreaction, "ChainReaction","Watch all the events unfold in an exquistie game of dominos, falling in tandem."));
        mostViewedLocations.add(new EventType.MostViewedHelperClass(R.drawable.golf, "Mini Golf","Put your way through fun filled holes of 3d designed coursers with elevated platforms."));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);

                                FeaturedData.add(new FeaturedHelperClass(
                                        product.getString("image"),
                                        product.getString("title"),
                                        product.getString("shortdesc")

                                ));
                            }

                            FeaturedAdapter adapter = new FeaturedAdapter(Dashboard.this, FeaturedData);
                            featuredRecycler.setAdapter(adapter);

                           /* adapter = new FeaturedAdapter(product);
                            featuredRecycler.setAdapter(adapter);*/

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


   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            //Toast.makeText(this, "Press one more time to exit", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        } else {    // this guy is serious
            // clean up
            moveTaskToBack(true);
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {
            Intent i = new Intent(Dashboard.this, DisplayEventTypes.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_news) {
            Intent i = new Intent(Dashboard.this, CampusNews.class);

            i.putExtra("extra", 2);
            startActivity(i);
        } else if (id == R.id.nav_map) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=12.948724, 77.573039"));
            startActivity(i);
        } else if (id == R.id.nav_view) {
            Intent i = new Intent(Dashboard.this, Login.class);
            startActivity(i);
        } else if (id == R.id.nav_mybooking) {
            Intent i = new Intent(Dashboard.this, DisplayMyEventsCollections.class);
            startActivity(i);
        }
//12.9487,77.5729
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void myevent(View view) {
        Intent intent = new Intent(this, DisplayMyEventsCollections.class);
        startActivity(intent);
    }

    public void AllEvents(View view) {
        Intent intent = new Intent(this, DisplayEventTypes.class);
        startActivity(intent);
    }

    public void Events1(View view) {
        Intent intent = new Intent(this, DisplayEventTypes.class);
        startActivity(intent);
    }


    public void Sponsers(View view) {
        Intent intent = new Intent(this, Sponsors.class);
        startActivity(intent);
    }

    public void Map(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=12.948724, 77.573039"));
        startActivity(intent);
    }

    public void CantactUs(View view) {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    public void CampusNews(View view) {
        Intent intent = new Intent(this, CampusNews.class);
        startActivity(intent);
    }

    public void EventsShow(View view) {
        Intent intent = new Intent(this, DisplayEventTypes.class);
        startActivity(intent);
    }

}
/*
public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    GridView grid;

    String rigntone;
    NotificationManager notificationManager;
    String uname;

    long backPressedTime = 0;
    private ObjectMapper objectMapper = new ObjectMapper();
    public static LoginResponse LoggedInUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Notification process
        //preference Notification
        //preference Notification
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        rigntone = sp.getString("ring1", "default ringtone");
        uname = sp.getString("name1", "User");
        //Dashboard.LoggedInUserInfo.getUuid();

        //  Toast.makeText(Dashboard.this, "Logged in as : " + uname, Toast.LENGTH_SHORT).show();
        showNotification();


        Intent intent = getIntent();
        if (intent.hasExtra("loginResponse")) {
            String loginResponse = intent.getStringExtra("loginResponse");
            try {
                LoggedInUserInfo = objectMapper.readValue(loginResponse, LoginResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // Intent it=new Intent(this,NotifyMain.class);
    */
/*    grid = (GridView) findViewById(R.id.gridViw);
        grid.setAdapter(new NavAdapter(this));*//*



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    */
/*  grid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent i = new Intent(Dashboard.this, DisplayEventTypes.class);
                    startActivity(i);
                } else if (position == 1) {
                    Intent i1 = new Intent(Dashboard.this, DisplayMyEventsCollections.class);
                    startActivity(i1);
                } else if (position == 2) {
                    Intent i1 = new Intent(Dashboard.this, CampusNews.class);
                    i1.putExtra("extra", 2);
                    startActivity(i1);
                } else if (position == 3) {
                    Intent i1 = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=24.517606,73.751581"));
                    startActivity(i1);
                }

            }
        });*//*


    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 1, new Intent(this, Dashboard.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Android")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("College Cultrual  App")
                .setSmallIcon(R.drawable.ic_date_range)
                .setSound(Uri.parse(rigntone))
                .setContentText("Explore the events")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(1);
    }

    public void about(View v) {
        Intent i1 = new Intent(Dashboard.this, AboutUs.class);
        startActivity(i1);
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


    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            //Toast.makeText(this, "Press one more time to exit", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        } else {    // this guy is serious
            // clean up
            moveTaskToBack(true);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {
            Intent i = new Intent(Dashboard.this, DisplayEventTypes.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_news) {
            Intent i = new Intent(Dashboard.this, CampusNews.class);

            i.putExtra("extra", 2);
            startActivity(i);
        } else if (id == R.id.nav_map) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=24.517606,73.751581"));
            startActivity(i);
        } else if (id == R.id.nav_view) {
            Intent i = new Intent(Dashboard.this, Login.class);
            startActivity(i);
        } else if (id == R.id.nav_mybooking) {
            Intent i = new Intent(Dashboard.this, DisplayMyEventsCollections.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
*/


/*class items {
    int images;
    String itemName;

    items(int images, String itemName) {
        this.images = images;
        this.itemName = itemName;
    }
}*/

/*class NavAdapter extends BaseAdapter {
    ArrayList<items> list;
    Context context;

    NavAdapter(Context context) {
        this.context = context;
        list = new ArrayList<items>();
        Resources res = context.getResources();
        String[] temp = res.getStringArray(R.array.abcd);
        int[] images = {R.drawable.western, R.drawable.unnamed, R.drawable.eye,
                R.drawable.girly, R.drawable.cultu};
        for (int i = 0; i < 5; i++) {
            items tempItems = new items(images[i], temp[i]);
            list.add(tempItems);
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView images;
        TextView texts;

        ViewHolder(View v) {
            images = (ImageView) v.findViewById(R.id.imageView2);
            texts = (TextView) v.findViewById(R.id.textItem);
        }

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        items tempo = (items) getItem(i);
        holder.images.setImageResource(tempo.images);
        holder.texts.setText(tempo.itemName);
        return row;
        }
    }*/

