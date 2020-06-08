package com.example.today.Events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.today.R;
import com.example.today.models.EventType;
import com.example.today.models.Events;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.today.backgroundWorker.Urls1.EDIT_EVENT_URL;

//import static com.example.today.backgroundWorker.Urls.EDIT_EVENT_URL;
public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    final Calendar myCalendar = Calendar.getInstance();
    private final Calendar calendar = Calendar.getInstance();
    private Events event;
    private TextView eventId;
    static EditText title, price, description, imageUrl, eventDate, eventTime;
    private static EventType[] eventTypeModels;
    private static String eventType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // eventType = getIntent().getStringExtra("type");
       // Log.println(Log.ERROR, "GET_EVENTS", eventType);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Intent intent = getIntent();
            event = objectMapper.readValue(intent.getStringExtra("response"), Events.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventId = findViewById(R.id.edtId);
        title = findViewById(R.id.edtTitle);
        description = findViewById(R.id.edtDesc);
        price = findViewById(R.id.edtPrice);
        eventDate = findViewById(R.id.edtDate);
        eventTime = findViewById(R.id.edtTime);



        eventId.setText(event.getId());
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        price.setText(event.getPrice());
        eventDate.setText(event.getDate());
        eventTime.setText(event.getTime());
// imageUrl.setText(event.getImageUrl());
//        initializeImage();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                eventDate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* new DatePickerDialog(CreateEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                String sDate = Calendar.YEAR + "/" + Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH;
                eventDate.setText(sDate);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, date, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);  //date is dateSetListener as per your code in question
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eventTime.setText(convertDate(selectedHour) + ":" + convertDate(selectedMinute) + ":00");
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }


    private String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


        public void updateEvent(View view) {
             UpdateEventBackground updateEventBackground = new UpdateEventBackground();
            updateEventBackground.execute(eventId.getText().toString(),title.getText().toString(), description.getText().toString(),
                    price.getText().toString(), eventDate.getText().toString(), eventTime.getText().toString());
        }

    public class UpdateEventBackground extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            Log.println(Log.ERROR, "EDIT_EVENTS", response+"\t RRRRRR");
            if ("Data Updated".equals(response.trim())) {
                Toast.makeText(EditActivity.this, "data update ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this, EventsManagement.class);
                intent.putExtra("type", event.getType());
                startActivity(intent);
            } else {
                Log.println(Log.ERROR, "Event creation failed", response);
            }
        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                String id = params[0];
                String title = params[1];
                String shortdesc = params[2];
                String price = params[3];
                String date = params[4];
                String time = params[5];
                URL url = new URL(EDIT_EVENT_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&"
                        + URLEncoder.encode("shortdesc", "UTF-8") + "=" + URLEncoder.encode(shortdesc, "UTF-8") + "&"
                        + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                        + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") ;
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
                Log.println(Log.ERROR, "UPDATE_EVENT", e.getLocalizedMessage());
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditActivity.this, EventsManagement.class);
        intent.putExtra("type", eventType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}