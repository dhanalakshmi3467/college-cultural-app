package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Login extends AppCompatActivity {
    EditText email, password;

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