package com.example.today;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.*;
import android.os.Bundle;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splashscreen extends AppCompatActivity {

    final int TIME = 2000;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_DeviceDefault_Panel);
        setContentView(R.layout.activity_splash);
        pb= (ProgressBar)findViewById(R.id.progressBar2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Splashscreen.this.startActivity(new Intent(Splashscreen.this,Login.class));
                pb.setProgress(50);
                pb.setProgress(100);
                Splashscreen.this.finish();
            }
        },(long) TIME);

    }
}
/*

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        setContentView(R.layout.activity_main2);
        //this will bind your MainActivity.class file with activity_main.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this,
                        MainActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}


//public class Splashscreen {
//}
*/
