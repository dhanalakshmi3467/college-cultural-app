package com.example.today;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Intent it=new Intent(this,NotifyMain.class);
        grid=(GridView)findViewById(R.id.gridViw);
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
        grid.setOnItemClickListener( new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(i);
                }
                   else if(position==1){
                        Intent i1 = new Intent(MainActivity.this, accc.class);
                        startActivity(i1);}
                else if(position==2){
                    Intent i1 = new Intent(MainActivity.this, ScheduleActivity.class);
                    i1.putExtra("extra",2);
                    startActivity(i1);
                }
              else if(position==3){
                    Intent i1 = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=24.517606,73.751581"));
                    startActivity(i1);
                }
                else if(position==4){
                    Intent i1 = new Intent(MainActivity.this, ContactUs.class);
                    startActivity(i1);
                }
                else if(position==5){
                    Intent i1 = new Intent(MainActivity.this, developer.class);
                    startActivity(i1);
                }

            }
        });

    }
    public void about(View v){
        Intent i1 = new Intent(MainActivity.this, AboutUs.class);
        startActivity(i1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(MainActivity.this, accc.class);
            startActivity(i);

        } else if (id == R.id.nav_schedule) {
            Intent i = new Intent(MainActivity.this, ScheduleActivity.class);

            i.putExtra("extra",2);
            startActivity(i);
        } else if (id == R.id.nav_map) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=24.517606,73.751581"));
            startActivity(i);

        } else if (id == R.id.nav_faqs) {
            Intent i = new Intent(MainActivity.this, developer.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(MainActivity.this, ContactUs.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
class items{
    int images;
    String itemName;
    items(int images,String itemName){
        this.images=images;
        this.itemName=itemName;
    }
}

class NavAdapter extends BaseAdapter{
    ArrayList<items> list;
    Context context;
    NavAdapter(Context context){
        this.context=context;
        list=new ArrayList<items>();
        Resources res=context.getResources();
        String[] temp= res.getStringArray(R.array.abcd);
        int[] images={R.drawable.ic_menu_camera,R.drawable.ic_menu_gallery,R.drawable.ic_menu_manage,
                R.drawable.ic_menu_send,R.drawable.ic_menu_share,R.drawable.ic_menu_slideshow};
        for(int i=0;i<6;i++){
            items tempItems=new items(images[i],temp[i]);
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
    class ViewHolder
    {
        ImageView images;
        TextView texts;
        ViewHolder(View v){
            images=(ImageView)v.findViewById(R.id.imageView2);
            texts=(TextView)v.findViewById(R.id.textItem);
        }

    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder= null;
        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_item, parent, false);
            holder=new ViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder=(ViewHolder)row.getTag();
        }
        items tempo=(items)getItem(i);
        holder.images.setImageResource(tempo.images);
        holder.texts.setText(tempo.itemName);
        return row;

    }
}