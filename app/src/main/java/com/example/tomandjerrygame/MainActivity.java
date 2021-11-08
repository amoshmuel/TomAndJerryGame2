package com.example.tomandjerrygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] path;
    private int[][] vals;
    private ImageView [] player;
    private ImageView [] live;
    private ImageButton btn_left;
    private ImageButton btn_right;
    private Timer timer = new Timer();
    private int clock = 1;
    private int  playerPosition =1;
    int flag = 1; //1 = first open
    final int DELAY = 2000;
    final Handler handler = new Handler();
    private Runnable timerRunnable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
/*
        ImageView demo_IMG_back = findViewById(R.id.IMG_back);
        Glide
                .with(this)
                .load(R.drawable.img_house_back)
                .centerCrop()
                .into(demo_IMG_back);
*/
        for (int i = 0; i < vals.length; i++) {
            for (int j = 0; j < vals[i].length; j++) {
                vals[i][j] = 0;
            }
        }

        initViews();
//check it
        timerRunnable = () -> {
            updateClockView();
            handler.postDelayed(timerRunnable, DELAY);
        };
    }





    private void initViews() {
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPosition == 0) {//player on the left move to mid
                    player[0].setVisibility(View.INVISIBLE);
                    player[1].setVisibility(View.VISIBLE);
                    playerPosition = 1;
                } else if (playerPosition == 1) {//player on the mid move to right
                    player[1].setVisibility(View.INVISIBLE);
                    player[2].setVisibility(View.VISIBLE);
                    playerPosition = 2;
                } else if (playerPosition == 2) { //player on the right cant move
                    playerPosition = 2;
                }
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPosition == 2) {//player on the right move to mid
                    player[2].setVisibility(View.INVISIBLE);
                    player[1].setVisibility(View.VISIBLE);
                    playerPosition = 1;
                } else if (playerPosition == 1) {//player on the mid move to left
                    player[1].setVisibility(View.INVISIBLE);
                    player[0].setVisibility(View.VISIBLE);
                    playerPosition = 0;
                } else if (playerPosition == 0) { //player on the right cant move
                    playerPosition = 0;
                }
            }
        });
    }

    private void findViews() {
        path = new ImageView[][]{
                {findViewById(R.id.demo_IMG_00), findViewById(R.id.demo_IMG_01), findViewById(R.id.demo_IMG_02)},
                {findViewById(R.id.demo_IMG_10), findViewById(R.id.demo_IMG_11), findViewById(R.id.demo_IMG_12)},
                {findViewById(R.id.demo_IMG_20), findViewById(R.id.demo_IMG_21), findViewById(R.id.demo_IMG_22)},
                {findViewById(R.id.demo_IMG_30), findViewById(R.id.demo_IMG_31), findViewById(R.id.demo_IMG_32)}
        };
        vals = new int[path.length][path[0].length];
        player = new ImageView[]{
                findViewById(R.id.panel_IMG_jerry0),
                findViewById(R.id.panel_IMG_jerry1),
                findViewById(R.id.panel_IMG_jerry2)
        };
        live = new ImageView[]{
                findViewById(R.id.panel_IMG_chesse1),
                findViewById(R.id.panel_IMG_chesse2),
                findViewById(R.id.panel_IMG_chesse3)
        };
        btn_left = findViewById(R.id.btn_left_panel);
        btn_right = findViewById(R.id.btn_right_panel);

    }


    private void checkCrash() {

        for (int i = 0; i < vals[vals.length - 1].length; i++) {
            if(vals[vals.length- 1][i] == 1 && playerPosition == i){
                for (int j = live.length - 1; j >= 0; j--) {
                    if(live[j].getVisibility() == View.VISIBLE){
                        live[j].setVisibility(View.INVISIBLE);
                        return;
                    }else if (live[0].getVisibility() == View.INVISIBLE){
                        onStop();
                    }
                }
            }
        }
    }

    private void runlogic() {
        for (int j = 0; j < vals[0].length; j++) {
            if(j == 0) {
                vals[0][j] = 1;
            } else {
                vals[0][j] = 0;
            }
        }
        shuffleArray(vals);
    }

    private void shuffleArray(int[][] vals) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = vals[0].length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            // Swap
            int a = vals[0][index];
            vals[0][index] = vals[0][i];
            vals[0][i] = a;
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();

    }

    private void stopTicker() {
        handler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
//        updateUI();
    }

    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < path.length; i++) {
                    for (int j = 0; j < path[i].length; j++) {
                        ImageView im = path[i][j];
                        if(vals[i][j] == 0){
                            path[i][j].setVisibility(View.INVISIBLE);
                        }else if (vals[i][j] == 1){
                            path[i][j].setVisibility(View.VISIBLE);
                            path[i][j].setImageResource(R.drawable.img_tom);
                        }
                    }
                }
            }
        });
    }

    private void startTicker() {
        handler.postDelayed(timerRunnable, DELAY);
    }

    private void updateClockView() {
        //update every 2 sec the matrix.
        checkCrash();
        runlogic();
        updateUI();
        moveForward();
    }

    private void moveForward() {
        for (int i = vals.length - 1; i >= 0; i--) {
            for (int j = 0; j < vals[i].length; j++){
                if(i > 0){
                    vals[i][j] = vals[i-1][j];
                }
            }
        }
    }
}
