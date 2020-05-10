package com.example.today;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

//import com.example.newlogin.Homepage;

import com.example.today.models.LoginResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.transform.Result;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.example.today.Urls.LOGIN_URL;

/**
 * Created by Programming Knowledge on 1/5/2016.
 */
public class LoginBackground extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;

    private Dialog loadingDialog;

    public static boolean admin = false;

    public LoginBackground(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if (type.equals("Login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(LOGIN_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
//                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        loadingDialog = ProgressDialog.show(context, "Please Wait", "Loading...");
    }

    @Override
    protected void onPostExecute(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            LoginResponse loginResponse = objectMapper.readValue(response,LoginResponse.class);
            if (loginResponse.isExists()){
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("loginResponse", response);
                context.startActivity(intent);
            }else{
                Toast.makeText(context, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        /*
        if ("admin".equals(result.trim())) {
//            Toast.makeText(context, result , Toast.LENGTH_LONG).show();
            admin = true;
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else if ("user".equals(result.trim())) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }*/
        loadingDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }
}