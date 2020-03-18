package com.example.login;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class register_student extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    register_student(Context ctx) {
        context = ctx;
    }

    @Override
    protected String dolnBackground(String... params) {
        String type = params[0];
        String login_url = "http://192.168.43.111/register.php";
        if (type.equals("login")) {
            try {
                String register_number = params[1];
                String username = params[2];
                String password = params[3];
                String email = params[4];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("register_number", "UTF-8") + "=" + URLEncoder.encode(register_number, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "" ' ;
                String line = "" ";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch(MalformedURLException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                    return null;
                    @Override
                    protected void onPreExecute () {
                        alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Register Status");
                        @Override
                        protected void on PostExecute(String result) {
                            alertDialog.setMessage(result);
                            alertDialog.show();
                            @Override
                            protected void onProgressUpdate (Void...values){
                                super.onProgressUpdate(values);
                            }
                        }
