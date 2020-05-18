package com.example.today;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.today.models.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;

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
        uname = sp.getString("name1","User" );
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
        grid = (GridView) findViewById(R.id.gridViw);
        grid.setAdapter(new NavAdapter(this));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        grid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent i = new Intent(Dashboard.this, DisplayEventTypes.class);
                    startActivity(i);
                } else if (position == 1) {
                    Intent i1 = new Intent(Dashboard.this, accc.class);
                    startActivity(i1);
                } else if (position == 2) {
                    Intent i1 = new Intent(Dashboard.this, CampusNews.class);
                    i1.putExtra("extra", 2);
                    startActivity(i1);
                } else if (position == 3) {
                    Intent i1 = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=24.517606,73.751581"));
                    startActivity(i1);
                } else if (position == 4) {
                    Intent i1 = new Intent(Dashboard.this, developer.class);
                    startActivity(i1);
                }

            }
        });

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
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(Dashboard.this, accc.class);
            startActivity(i);

        } else if (id == R.id.nav_schedule) {
            Intent i = new Intent(Dashboard.this, CampusNews.class);

            i.putExtra("extra", 2);
            startActivity(i);
        } else if (id == R.id.nav_map) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=24.517606,73.751581"));
            startActivity(i);

        } else if (id == R.id.nav_faqs) {
            Intent i = new Intent(Dashboard.this, developer.class);
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


class items {
    int images;
    String itemName;

    items(int images, String itemName) {
        this.images = images;
        this.itemName = itemName;
    }
}

class NavAdapter extends BaseAdapter {
    ArrayList<items> list;
    Context context;

    NavAdapter(Context context) {
        this.context = context;
        list = new ArrayList<items>();
        Resources res = context.getResources();
        String[] temp = res.getStringArray(R.array.abcd);
        int[] images = {R.drawable.western, R.drawable.unnamed, R.drawable.eye,
                R.drawable.girly, R.drawable.cultu, R.drawable.color};
        for (int i = 0; i < 6; i++) {
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
}
