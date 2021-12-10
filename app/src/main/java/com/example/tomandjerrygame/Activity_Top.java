package com.example.tomandjerrygame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Top extends AppCompatActivity {

    public static final String NAME = "NAME";
    public static final String GENDER = "GENDER";
    public static final String LOCATION = "LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        String name = getIntent().getStringExtra(NAME);
        String gender = getIntent().getStringExtra(GENDER);
        String location = getIntent().getStringExtra(LOCATION);

    }
}