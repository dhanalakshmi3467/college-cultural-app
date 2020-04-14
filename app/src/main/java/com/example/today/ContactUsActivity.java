package com.example.today;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ContactUsActivity extends Fragment {

    EditText editText;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_contactus, container, false);

        editText = (EditText) view.findViewById(R.id.edit_contact_us);
//        editText.addTextChangedListener();
        return view;
    }

    /*EditText edit_contact_us;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        btn = findViewById(R.id.button);
        edit_contact_us = findViewById(R.id.edit_contact_us);
    }*/
}
