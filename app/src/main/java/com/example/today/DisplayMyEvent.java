package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.today.models.EventBookResponse;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.today.Urls.UNREGISTER_EVENT_URL;

public class DisplayMyEvent extends AppCompatActivity {

    private TextView id, title, description, price, date, time, imageUrl;
    private Events event;
    private Button unregister;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_event);

        Intent intent = getIntent();
        ObjectMapper objectMapper = new ObjectMapper();

        id = findViewById(R.id.myEventDetailId);
        title = findViewById(R.id.myEventDetailTitle);
        description = findViewById(R.id.myEventDetailDescription);
        price = findViewById(R.id.myEventDetailPrice);
        date = findViewById(R.id.myEventDetailDate);
        time = findViewById(R.id.myEventDetailTime);
        imageUrl = findViewById(R.id.myEventDetailImageUrl);
        unregister = findViewById(R.id.myEventUnregister);
        try {
            event = objectMapper.readValue(intent.getStringExtra("response"), Events.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        id.setText(event.getId());
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        price.setText(event.getPrice());
        date.setText(event.getDate());
        time.setText(event.getTime());
        imageUrl.setText(event.getImageUrl());

        id.setVisibility(View.GONE);

        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayMyEvent.this);
                alertDialog.setTitle("Unregister event");
                alertDialog.setMessage("Please click confirm to unregister.");
                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        unRegisterEvent(event.getId(), MainActivity.LoggedInUserInfo.getEmail());
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void unRegisterEvent(final String eventId, String userId) {

        class Unregister extends AsyncTask<String,Void,String> {

            private Context context;
            private String userId;
            private String eventId;
            AlertDialog alertDialog;
            private Dialog loadingDialog;

            public Unregister(Context context, String eventId, String userId) {
                this.context = context;
                this.eventId = eventId;
                this.userId = userId;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                alertDialog = new AlertDialog.Builder(DisplayMyEvent.this).create();
                loadingDialog = ProgressDialog.show(DisplayMyEvent.this, "Please Wait", "Registering...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                alertDialog.setTitle("Event Unregistration");
                alertDialog.setCanceledOnTouchOutside(false);
                loadingDialog.dismiss();
                EventBookResponse eventBookResponse;
                try {
                    Log.println(Log.INFO, "unregister event", s);
                    eventBookResponse = objectMapper.readValue(s, EventBookResponse.class);
                    if (!eventBookResponse.isBooked()) {
                        alertDialog.setMessage(eventBookResponse.getMessage());
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                    } else {
                        alertDialog.dismiss();
                        Toast.makeText(DisplayMyEvent.this, eventBookResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(DisplayMyEvent.this, DisplayMyEventsCollections.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    Log.println(Log.ERROR, "unregister response", eventId + "\t" + userId);
                    URL url = new URL(UNREGISTER_EVENT_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("eventId", "UTF-8") + "=" + URLEncoder.encode(eventId, "UTF-8") + "&"
                            + URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        Unregister unregister = new Unregister(DisplayMyEvent.this, eventId, userId);
        unregister.execute();
    }
}
