package com.example.today.backgroundWorker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.today.activities.Login;

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

import static com.example.today.backgroundWorker.Urls1.REG_URL;

public class RegisterBackground extends AsyncTask<String,String,String> {
    Context context;

    public RegisterBackground(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... String) {
        String type = String[0];
        String register_number = String[1];
        String username = String[2];
        String password = String[3];
        String email = String[4];
        String admin = String[5];

        if (type.equals("reg")) {
            try {

                URL url = new URL(REG_URL);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String insert_data = URLEncoder.encode("register_number", "UTF-8") + "=" + URLEncoder.encode(register_number, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("admin", "UTF-8") + "=" + URLEncoder.encode(admin, "UTF-8");
                    bufferedWriter.write(insert_data);
                    // intent.putExtra("type", admin.getText().toString())
                    if (null != bufferedWriter) {
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    result = stringBuilder.toString();

                    if (null != bufferedReader) {
                        bufferedReader.close();
                    }

                    if (null != inputStream) {
                        inputStream.close();
                    }

                    if (null != httpURLConnection) {
                        httpURLConnection.disconnect();
                    }
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        if ("registration succesfully completed".equals(s.trim())) {
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);

        }
        //  super.onPostExecute(s);


    }
}
