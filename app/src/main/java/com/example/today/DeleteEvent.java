package com.example.today;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.today.Urls.CREATE_EVENT_URL;
import static com.example.today.Urls.DELETE_EVENT_URL;

public class DeleteEvent extends AsyncTask<String,Void,String> {
    Context context;
    String eventType;

    public DeleteEvent(Context ctx, String eventType) {
        this.context = ctx;
        this.eventType = eventType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String response) {
        if ("Data Deleted".equals(response.trim())) {
            Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, EventsManagement.class);
            intent.putExtra("type", eventType);
            context.startActivity(intent);
        } else {
            Log.println(Log.ERROR, "Event deletion failed", response);
        }
    }

    @Override
    protected String doInBackground(String[] params) {
        try {
            String id = params[0];
            URL url = new URL(DELETE_EVENT_URL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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

        } catch (Exception e) {
            Log.println(Log.ERROR, "DELETE_EVENT", e.getLocalizedMessage());
        }
        return null;
    }
}

