package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import static com.example.today.Urls.IS_EVENT_BOOKED_URL;
import static com.example.today.Urls.REGISTER_EVENT_URL;
import static com.example.today.Urls.UNREGISTER_EVENT_URL;

public class DisplayEvent extends AppCompatActivity {

    private TextView id, title, description, price, date, time, imageUrl;
    private Events event;
    private static final String REGISTER = "Register";
    private static final String UN_REGISTER = "Unregister";
    private Button register;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            event = objectMapper.readValue(intent.getStringExtra("response"), Events.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        id = findViewById(R.id.eventDetailId);
        title = findViewById(R.id.eventDetailTitle);
        description = findViewById(R.id.eventDetailDescription);
        price = findViewById(R.id.eventDetailPrice);
        date = findViewById(R.id.eventDetailDate);
        time = findViewById(R.id.eventDetailTime);
        imageUrl = findViewById(R.id.eventDetailImageUrl);
        register = findViewById(R.id.registerEvent);

        id.setText(event.getId());
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        price.setText(event.getPrice());
        date.setText(event.getDate());
        time.setText(event.getTime());
        imageUrl.setText(event.getImageUrl());

        id.setVisibility(View.GONE);


        if (event.getCreatedBy() == Integer.parseInt(Dashboard.LoggedInUserInfo.getUuid()) && !Dashboard.LoggedInUserInfo.isAdmin()) {
            register.setVisibility(View.GONE);
        }

        checkIfEventIsBooked();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.ERROR, "DELETE_ME", register.getText().toString());
                if (REGISTER.equals(register.getText().toString())) {
                    registerEvent(event.getId(), Dashboard.LoggedInUserInfo.getEmail());
                } else {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayEvent.this);
                    alertDialog.setTitle("Unregister event");
                    alertDialog.setMessage("Please click confirm to unregister.");
                    alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            unRegisterEvent(event.getId(), Dashboard.LoggedInUserInfo.getEmail());
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    private void checkIfEventIsBooked() {

        String url = String.format("%s?eventId=%s&userId=%s", IS_EVENT_BOOKED_URL, event.getId(), Dashboard.LoggedInUserInfo.getEmail());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            Log.println(Log.ERROR, "GET_EVENTS", response);
                            EventBookResponse eventBookResponse = objectMapper.readValue(response, EventBookResponse.class);
                            if (eventBookResponse.isBooked()) {
                                register.setText(UN_REGISTER);
                            } else {
                                register.setText(REGISTER);
                            }
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

    private void registerEvent(final String eventId, String userId) {

        class RegisterEvent extends AsyncTask<String,Void,String> {

            AlertDialog alertDialog;
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                alertDialog = new AlertDialog.Builder(DisplayEvent.this).create();
                loadingDialog = ProgressDialog.show(DisplayEvent.this, "Please Wait", "Registering...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                alertDialog.setTitle("Event Registration");
                alertDialog.setCanceledOnTouchOutside(false);
                loadingDialog.dismiss();
                EventBookResponse eventBookResponse;
                try {
                    Log.println(Log.INFO, "register event response", s);
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
                        register.setText(UN_REGISTER);
                        Toast.makeText(DisplayEvent.this, eventBookResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String eventId = params[0];
                    String userId = params[1];
                    Log.println(Log.ERROR, "response natasss", eventId + "\t" + userId);
                    URL url = new URL(REGISTER_EVENT_URL);
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
        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.execute(eventId, userId);
    }

    private void unRegisterEvent(final String eventId, String userId) {

        class Unregister extends AsyncTask<String,Void,String> {

            AlertDialog alertDialog;
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                alertDialog = new AlertDialog.Builder(DisplayEvent.this).create();
                loadingDialog = ProgressDialog.show(DisplayEvent.this, "Please Wait", "Registering...");
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
                        register.setText(REGISTER);
                        Toast.makeText(DisplayEvent.this, eventBookResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String eventId = params[0];
                    String userId = params[1];
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
        Unregister unregister = new Unregister();
        unregister.execute(eventId, userId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DisplayEvent.this, EventsManagement.class);
        intent.putExtra("type", event.getType());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
