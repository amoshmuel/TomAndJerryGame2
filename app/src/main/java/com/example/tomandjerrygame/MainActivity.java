package com.example.tomandjerrygame;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView[][] panel_img_obstacle;
    private int[][] vals;
    private ImageView [] player;
    private ImageView [] live;
    private ImageButton btn_left;
    private ImageButton btn_right;
    private int  playerPosition =1;
    final int DELAY = 1000;
    final Handler handler = new Handler();
    private Runnable timerRunnable;
    private final int VIBRATE_TIME = 500;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        for (int i = 0; i < vals.length; i++) {
            for (int j = 0; j < vals[i].length; j++) {
                vals[i][j] = 0;
            }
        }
        initViews();

        timerRunnable = () -> {
            updateClockView();
            handler.postDelayed(timerRunnable, DELAY);
        };
    }





    private void initViews() {
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate(20);
                if (playerPosition == 0) {//player on the left move to mid
                    player[0].setVisibility(View.INVISIBLE);
                    player[1].setImageResource(R.drawable.img_jerry);
                    player[1].setVisibility(View.VISIBLE);
                    playerPosition = 1;
                } else if (playerPosition == 1) {//player on the mid move to right
                    player[1].setVisibility(View.INVISIBLE);
                    player[2].setImageResource(R.drawable.img_jerry);
                    player[2].setVisibility(View.VISIBLE);
                    playerPosition = 2;
                } else if (playerPosition == 2) { //player on the right cant move
                    player[2].setImageResource(R.drawable.img_jerry);
                    player[2].setVisibility(View.VISIBLE);
                    playerPosition = 2;
                }
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate(20);
                if (playerPosition == 2) {//player on the right move to mid
                    player[2].setVisibility(View.INVISIBLE);
                    player[1].setImageResource(R.drawable.img_jerry);
                    player[1].setVisibility(View.VISIBLE);
                    playerPosition = 1;
                } else if (playerPosition == 1) {//player on the mid move to left
                    player[1].setVisibility(View.INVISIBLE);
                    player[0].setImageResource(R.drawable.img_jerry);
                    player[0].setVisibility(View.VISIBLE);
                    playerPosition = 0;
                } else if (playerPosition == 0) { //player on the right cant move
                    player[0].setImageResource(R.drawable.img_jerry);
                    player[0].setVisibility(View.VISIBLE);
                    playerPosition = 0;
                }
            }
        });
    }

    private void findViews() {
        panel_img_obstacle = new ImageView[][]{
                {findViewById(R.id.panel_img_tom_00), findViewById(R.id.panel_img_tom_01), findViewById(R.id.panel_img_tom_02)},
                {findViewById(R.id.panel_img_tom_10), findViewById(R.id.panel_img_tom_11), findViewById(R.id.panel_img_tom_12)},
                {findViewById(R.id.panel_img_tom_20), findViewById(R.id.panel_img_tom_21), findViewById(R.id.panel_img_tom_22)},
                {findViewById(R.id.panel_img_tom_30), findViewById(R.id.panel_img_tom_31), findViewById(R.id.panel_img_tom_32)},
                {findViewById(R.id.panel_img_jerry0), findViewById(R.id.panel_img_jerry1), findViewById(R.id.panel_img_jerry2)}
        };
        vals = new int[panel_img_obstacle.length][panel_img_obstacle[0].length];
        player = new ImageView[]{
                findViewById(R.id.panel_img_jerry0),
                findViewById(R.id.panel_img_jerry1),
                findViewById(R.id.panel_img_jerry2)
        };
        live = new ImageView[]{
                findViewById(R.id.panel_img_chesse1),
                findViewById(R.id.panel_img_chesse2),
                findViewById(R.id.panel_img_chesse3)
        };
        btn_left = findViewById(R.id.panel_btn_left);
        btn_right = findViewById(R.id.panel_btn_right);

    }


    private void checkCrash() {
        for (int i = 0; i < vals[vals.length - 1].length; i++) {
            if(vals[vals.length- 1][i] == 1 && playerPosition == i){
                for (int j = live.length - 1; j >= 0; j--) {
                    if(live[j].getVisibility() == View.VISIBLE){
                        live[j].setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "HIT!", Toast.LENGTH_SHORT).show();
                        vibrate(VIBRATE_TIME);
                        return;
                    }else if (live[0].getVisibility() == View.INVISIBLE){
                        stopTicker();
                    }
                }
            }
        }
    }

    private void vibrate(int timer) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATE_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(timer);
        }
    }


    private void runlogic() {
        if(counter++ %2 == 0){
            Random r = new Random();
            vals[0][r.nextInt(vals[0].length)] =1 ;
//            for (int j = 0; j < vals[0].length; j++) {
//                if(j == 0) {
//                    vals[0][j] = 1;
//                } else {
//                    vals[0][j] = 0;
//                }
//            }
//            shuffleArray(vals);
        }else {
            for (int j = 0; j < vals[0].length; j++) {
                vals[0][j] = 0;
            }
        }
    }

    private void shuffleArray(int[][] vals) {
        Random rand = new Random();
        for (int i = 0; i < vals[0].length; i++) {
            int randomIndexToSwap = rand.nextInt(vals[0].length);
            int temp = vals[0][randomIndexToSwap];
            vals[0][randomIndexToSwap] = vals[0][i];
            vals[0][i] = temp;
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
    }

    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < panel_img_obstacle.length; i++) {
                    for (int j = 0; j < panel_img_obstacle[i].length; j++) {
                        ImageView im = panel_img_obstacle[i][j];
                        if(vals[i][j] == 0){
                            panel_img_obstacle[i][j].setVisibility(View.INVISIBLE);
                            player[playerPosition].setImageResource(R.drawable.img_jerry);
                            player[playerPosition].setVisibility(View.VISIBLE);
                        }else if (vals[i][j] == 1){
                            panel_img_obstacle[i][j].setVisibility(View.VISIBLE);
                            panel_img_obstacle[i][j].setImageResource(R.drawable.img_tom);
                            player[playerPosition].setImageResource(R.drawable.img_jerry);
                            player[playerPosition].setVisibility(View.VISIBLE);
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
