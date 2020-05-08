package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;


public class Login extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        UsernameEt = (EditText) findViewById(R.id.txtUsername);
        PasswordEt = (EditText) findViewById(R.id.txtPass);

//for validation



    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "Login";
        if(CheckValidation()){
            LoginBackground register = new LoginBackground(this);
            register.execute(type, username, password);
        }
    }

    public void signUpButton(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

    public boolean CheckValidation(){
        boolean ret = true;
        if (!Validation.isUsername(UsernameEt, true)) ret=false;
        if (!Validation.isPassword(PasswordEt,true)) ret=false;
        return ret;
    }
}