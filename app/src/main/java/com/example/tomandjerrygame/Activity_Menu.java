package com.example.tomandjerrygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Activity_Menu extends AppCompatActivity {

    private MaterialButton menu_BTN_acc;
    private MaterialButton menu_BTN_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_BTN_acc = findViewById(R.id.menu_BTN_acc);
        menu_BTN_light = findViewById(R.id.menu_BTN_light);

        menu_BTN_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("ACC");
            }
        });

        menu_BTN_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("LIGHT");
            }
        });
    }

    private void startGame(String sns) {
        Intent myIntent = new Intent(this, MainActivity.class);

        Bundle bundle = new Bundle();
//        bundle.putString(MainActivity.SENSOR_TYPE,sns);
        bundle.putString(Activity_Summary.NAME, "Guy");
        bundle.putString(Activity_Summary.GENDER, "Female");

        myIntent.putExtra("Bundle", bundle);
        startActivity(myIntent);
    }


}