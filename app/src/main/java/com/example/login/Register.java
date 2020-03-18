package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {
    int flag = 0;
    EditText register_number, username, password, email;
    String str_register_number, str_username, str_password, str_email;
    public static String id, register_id, login_username, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        register_number = (EditText) findViewById(R.id.Register_Number);
        username = (EditText) findViewById(R.id.UserName);
        password = (EditText) findViewById(R.id.Password);
        email = (EditText) findViewById(R.id.email);
        Button reg_button = (Button) findViewById(R.id.reg_button);
        public void Register1 (View view)
        str_register_number = register_number.getText().toString();
        str_username = username.getText().toString();
        str_password = password.getText().toString();
        str_email = email.getText().toString();
        if (TextUtils.isEmpty(str_register_number)) {
            flag = 1;
            Toast.makeText(register this, "Enter Rregister-Number", Toast.LENGTH_LONG).show();
            if (TextUtils.isEmpty(str_username)) {
                flag = 1;
                Toast.makeText(register.this, "Enter Username", Toast.LENGTH_LONG).show();
// Snackbar snackbar1 = (Snackbar) Snackbar.make(register.this, "Enter Username", Snackbar.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(str_password)) {
                    flag = 1;
                    Toast.makeText(register.this, "Enter Password", Toast.LENGTH_LONG).show();
                    if (TextUtils.isEmpty(str_email)) {
                        flag = 1;
                        Toast.makeText(register.this, "Enter Email", Toast.LENGTH_LONG).show();
                        if (flag == 0) {
                            String type = "login";
                            register_student backgroungWorker = new register_student(this);
                            try {
                                String r = backgroungWorker.execute(type, str_register_number, str_username, str_password, str_email).get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }
}
