package com.example.today;

import android.os.StrictMode;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.today.Urls.GET_EVENT_TYPE_URL;

public class EventType {


    public com.example.today.models.EventType[] getEventTypes() throws Exception {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        HttpURLConnection urlConnection = null;
        URL url = new URL(GET_EVENT_TYPE_URL);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        String jsonString = sb.toString();
        Log.println(Log.INFO, "EVENT_TYPE_RESPONSE", jsonString);
        return objectMapper.readValue(jsonString, com.example.today.models.EventType[].class);
    }
}
