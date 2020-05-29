package com.example.today;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


public class Register extends AppCompatActivity {

    Button _btnReg, _btnLogin;
    private ProgressBar progressBar;
    EditText _txtRegNum, _txtUserName, _txtEmail, _txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _btnReg = (Button) findViewById(R.id.btnReg);
        _btnLogin = (Button) findViewById(R.id.btnLogin);
        _txtRegNum = (EditText) findViewById(R.id.txtRegNum);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        _txtEmail = (EditText) findViewById(R.id.txtEmail);
        _txtUserName = (EditText) findViewById(R.id.txtName);
        _txtPass = (EditText) findViewById(R.id.txtPass);


    }

    public void signUpButton(View view) {
        String regNum = _txtRegNum.getText().toString();
        String email = _txtEmail.getText().toString().trim();
        String username = _txtUserName.getText().toString();
        String password = _txtPass.getText().toString().trim();
        if (CheckValidation()) {
            RegisterBackground backgroundWorker = new RegisterBackground(this);
            backgroundWorker.execute("reg", regNum, username, password, email, "0");
        }

    }

    public void signInButton(View view) {

        startActivity(new Intent(Register.this, Login.class));
    }

    public boolean CheckValidation() {
        boolean ret = true;
        if (!Validation.hasText(_txtRegNum)) ret = false;
        if (!Validation.isUsername(_txtUserName, true)) ret = false;
        if (!Validation.isEmailAddress(_txtEmail, true)) ret = false;
        if (!Validation.isPassword(_txtPass, true)) ret = false;
        return ret;
    }
}

