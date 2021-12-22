package com.example.tomandjerrygame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tomandjerrygame.CallBack.CallBackList;
import com.example.tomandjerrygame.Frag.FragListTop;
import com.example.tomandjerrygame.Frag.FragMap;
import com.example.tomandjerrygame.R;

public class ActivityTop extends AppCompatActivity {

    private FragListTop fragmentList;
    private FragMap fragmentMap;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        findViews();
        initButton();
        fragmentList = new FragListTop();
        fragmentList.setCallBackList(callBackList);
        getSupportFragmentManager().beginTransaction().add(R.id.top_frame_name, fragmentList).commit();

        fragmentMap = new FragMap();
        getSupportFragmentManager().beginTransaction().add(R.id.top_frame_map, fragmentMap).commit();
    }

    private void initButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(ActivityTop.this, ActivityMenu.class);
                startActivity(Intent);
                ActivityTop.this.finish();
            }
        });
    }

    private void findViews() {
        back = findViewById(R.id.top_img_back);
    }

    CallBackList callBackList = new CallBackList() {
        @Override
        public void recordData(String name, int score, double latitude, double longitude) {
            fragmentMap.zoom(name,score,latitude,longitude);
        }
    };

}