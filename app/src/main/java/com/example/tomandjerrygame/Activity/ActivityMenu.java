package com.example.tomandjerrygame.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tomandjerrygame.Flags.Flags;
import com.example.tomandjerrygame.R;

public class ActivityMenu extends AppCompatActivity {
    private boolean flagReg = true;
    private MediaPlayer startGame;
    private Button menuBtnSensor;
    private Button menuBtnRegular;
    private Button menuBtntop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        playMusic();
        initViews();
    }

    private void playMusic() {
        startGame.start();
    }

    private void initViews() {
        menuBtnSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("sensor");
            }
        });

        menuBtnRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("regular");
            }
        });

        menuBtntop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("top");
            }
        });
    }

    private void findViews() {
        menuBtnSensor = findViewById(R.id.menu_BTN_sensor);
        menuBtnRegular = findViewById(R.id.menu_BTN_regular);
        menuBtntop = findViewById(R.id.menu_BTN_top);
        startGame = MediaPlayer.create(this,R.raw.tom_and_jerry_tune);
    }


    private void startGame(String sns) {
        startGame.stop();
        Intent myIntent = new Intent();
        Bundle bundle = initBundleData(sns);
        if(sns.equals("top")){
            myIntent = new Intent(this, ActivityTop.class);
        }else if(sns.equals("sensor") || sns.equals("regular")){
            myIntent = new Intent(this, ActivityMain.class);
        }
        myIntent.putExtra(Flags.BUNDLE, bundle);
        startActivity(myIntent);
        ActivityMenu.this.finish();
    }

    private Bundle initBundleData(String sns) {
        Bundle bundle = new Bundle();
        if (sns.equals("sensor"))
            flagReg = false;
        bundle.putBoolean(Flags.FLAGREG, flagReg);
        return bundle;
        }
}
