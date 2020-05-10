package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.today.models.EventType;

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

import static com.example.today.Urls.CREATE_EVENT_URL;

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /*private Button buttonChooseImage;
    private Bitmap bitmap;*/
    final Calendar myCalendar = Calendar.getInstance();
    private final Calendar calendar = Calendar.getInstance();
 //   private ImageView image;
    //    Spinner eventType;
    static EditText title, price, description, imageUrl, eventDate, eventTime;
    private static EventType[] eventTypeModels;
    private static String eventType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventType = getIntent().getStringExtra("type");
        title = findViewById(R.id.createEventTitle);
        description = findViewById(R.id.createEventDescription);
        price = findViewById(R.id.createEventPrice);
        eventDate = findViewById(R.id.createEventDate);
        eventTime = findViewById(R.id.StartTime);

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
                new DatePickerDialog(CreateEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eventTime.setText(convertDate(selectedHour) + ":" + convertDate(selectedMinute) + ":00");
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

//        eventType = findViewById(R.id.eventType);

        try {
            eventTypeModels = new com.example.today.EventType().getEventTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* String[] eventTypeList = getEventType();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateEvent.this,
                android.R.layout.simple_spinner_item, eventTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.eventType.setAdapter(adapter);*/

    }

    private String[] getEventType() {
        String[] events = new String[eventTypeModels.length];
        int i = 0;
        for (EventType eventTypeModel : eventTypeModels) {
            events[i] = eventTypeModel.getType();
            i++;
        }
        return events;
    }

    private String getEventTypeId(String eventType) {
        for (EventType eventTypeModel : eventTypeModels) {
            if (eventType.equals(eventTypeModel.getType())) {
                Log.println(Log.INFO, "GET_EVENT_ID_BY_TYPE", "Event id " + eventTypeModel.getId() + " found for " + eventType);
                return eventTypeModel.getId();
            }
        }
        Log.println(Log.ERROR, "GET_EVENT_ID_BY_TYPE", "Unable to find event id using event type");
        return null;
    }


   /* private void initializeImage() {
        buttonChooseImage = (Button) findViewById(R.id.choose_image_button);
        image = (ImageView) findViewById(R.id.createEventImageUrl);
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 555);
            }
        });
    }*/

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

    public void eventCreate(View view) {
        CreateEventBackground createEventBackground = new CreateEventBackground();
        createEventBackground.execute(title.getText().toString(), description.getText().toString(),
                price.getText().toString(), eventType, eventDate.getText().toString(), eventTime.getText().toString(), Dashboard.LoggedInUserInfo.getUuid());
    }

    public class CreateEventBackground extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            if ("event created".equals(response.trim())) {
                Toast.makeText(CreateEvent.this, "Event created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateEvent.this,EventsManagement.class);
                intent.putExtra("type", eventType);
                startActivity(intent);
            } else {
                Log.println(Log.ERROR, "Event creation failed", response);
            }
        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                String title = params[0];
                String description = params[1];
                String price = params[2];
                String type = params[3];
                String date = params[4];
                String time = params[5];
                String createdBy = params[6];
                URL url = new URL(CREATE_EVENT_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&"
                        + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"
                        + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") + "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                        + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")+ "&"
                        + URLEncoder.encode("created_by", "UTF-8") + "=" + URLEncoder.encode(createdBy, "UTF-8");
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
                Log.println(Log.ERROR, "CREATE_EVENT", e.getLocalizedMessage());
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateEvent.this,EventsManagement.class);
        intent.putExtra("type", eventType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
