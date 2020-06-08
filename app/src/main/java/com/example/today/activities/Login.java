package com.example.today.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.today.R;
import com.example.today.backgroundWorker.LoginBackground;
import com.example.today.backgroundWorker.Validation;


public class Login extends AppCompatActivity {
    EditText email, password;
    long backPressedTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPass);

//for validation
    }

    public void OnLogin(View view) {
        String username = email.getText().toString();
        String password = this.password.getText().toString();
        String type = "Login";
        if (CheckValidation()) {
            LoginBackground register = new LoginBackground(this);
            register.execute(type, username, password);
        }
    }
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            //Toast.makeText(this, "Press one more time to exit", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        } else {    // this guy is serious
            // clean up
            moveTaskToBack(true);
        }
    }
    public void signUpButton(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

    public boolean CheckValidation() {
        boolean ret = true;
        if (!Validation.isEmailAddress(email, true)) ret = false;
        if (!Validation.isPassword(password, true)) ret = false;
        return ret;
    }
}