package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GalleryActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery1);

    textView = findViewById(R.id.textView);

    //This will bring in your input from Activity 1 and Display it in Activity 2

    String getMsg = getIntent().getStringExtra("data");

    // It will be shown in the text view by writing the code below...
        textView.setText(getMsg);
}

 /*   public void day1(View view) {
    }*/
 /*public void day1(View v) {
     switch(v.getId()) {
         case R.id.btnContactDev:
             Intent myIntent = new Intent();
             myIntent.setClassName(your_package_name_string, your_activity_name_string);
             // for ex: your package name can be "com.example"
             // your activity name will be "com.example.Contact_Developer"
             startActivity(myIntent);
             break;
     }
 }*/
}


