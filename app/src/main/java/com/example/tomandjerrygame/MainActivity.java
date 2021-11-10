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
    private int[][] vals_matrix_loc;
    private ImageView [] panel_img_player;
    private ImageView [] panel_img_live;
    private ImageButton btn_left;
    private ImageButton btn_right;
    private int  playerPosition =1;
    private final int DELAY = 1000;
    private final Handler handler = new Handler();
    private Runnable timerRunnable;
    private final int VIBRATE_TIME = 500;
    private int counter = 0;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        for (int i = 0; i < vals_matrix_loc.length; i++) {
            for (int j = 0; j < vals_matrix_loc[i].length; j++) {
                vals_matrix_loc[i][j] = 0;
            }
        }
        initViews();

        timerRunnable = () -> {
            if(flag == true){
                updateClockView();
                handler.postDelayed(timerRunnable, DELAY);
            }else{
                stopTicker();
            }
        };
    }





    private void initViews() {
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPosition == 0) {//player on the left move to mid
                    setVisibleFromTo(panel_img_player,0,1);

                } else if (playerPosition == 1) {//player on the mid move to right
                    setVisibleFromTo(panel_img_player,1,2);

                } else if (playerPosition == 2) { //player on the right cant move
                    setVisibleFromTo(panel_img_player,2,2);
                }
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPosition == 2) {//player on the right move to mid
                    setVisibleFromTo(panel_img_player,2,1);

                } else if (playerPosition == 1) {//player on the mid move to left
                    setVisibleFromTo(panel_img_player,1,0);

                } else if (playerPosition == 0) { //player on the right cant move
                    setVisibleFromTo(panel_img_player,0,0);
                }
            }
        });
    }

    private void setVisibleFromTo(ImageView [] player ,int from, int to) {
//        move the player from current location to new location
        if(to != from){
            player[from].setVisibility(View.INVISIBLE);
        }
        player[to].setImageResource(R.drawable.img_jerry);
        player[to].setVisibility(View.VISIBLE);
        playerPosition = to;
    }


    private void findViews() {
        panel_img_obstacle = new ImageView[][]{
                {findViewById(R.id.panel_img_tom_00), findViewById(R.id.panel_img_tom_01), findViewById(R.id.panel_img_tom_02)},
                {findViewById(R.id.panel_img_tom_10), findViewById(R.id.panel_img_tom_11), findViewById(R.id.panel_img_tom_12)},
                {findViewById(R.id.panel_img_tom_20), findViewById(R.id.panel_img_tom_21), findViewById(R.id.panel_img_tom_22)},
                {findViewById(R.id.panel_img_tom_30), findViewById(R.id.panel_img_tom_31), findViewById(R.id.panel_img_tom_32)},
                {findViewById(R.id.panel_img_jerry0), findViewById(R.id.panel_img_jerry1), findViewById(R.id.panel_img_jerry2)}
        };
        vals_matrix_loc = new int[panel_img_obstacle.length][panel_img_obstacle[0].length];
        panel_img_player = new ImageView[]{
                findViewById(R.id.panel_img_jerry0),
                findViewById(R.id.panel_img_jerry1),
                findViewById(R.id.panel_img_jerry2)
        };
        panel_img_live = new ImageView[]{
                findViewById(R.id.panel_img_chesse1),
                findViewById(R.id.panel_img_chesse2),
                findViewById(R.id.panel_img_chesse3)
        };
        btn_left = findViewById(R.id.panel_btn_left);
        btn_right = findViewById(R.id.panel_btn_right);

    }


    private void checkCrash() {
        //        check if was an object on the player postion.
        //        and remove the last cheese-live from the array.
        if(vals_matrix_loc[vals_matrix_loc.length- 1][playerPosition] == 1){
            for (int j = panel_img_live.length - 1; j >= 0; j--) {
                if(panel_img_live[j].getVisibility() == View.VISIBLE){
                    panel_img_live[j].setVisibility(View.INVISIBLE);
                    if(j != 0 ){
                        Toast.makeText(MainActivity.this, "HIT!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                    vibrate(VIBRATE_TIME);
                    return;
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
            vals_matrix_loc[0][r.nextInt(vals_matrix_loc[0].length)] =1 ;
        }else {
            for (int j = 0; j < vals_matrix_loc[0].length; j++) {
                vals_matrix_loc[0][j] = 0;
            }
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
                        if(vals_matrix_loc[i][j] == 0){
                            panel_img_obstacle[i][j].setVisibility(View.INVISIBLE);
                            setVisibleFromTo(panel_img_player,playerPosition,playerPosition);
                        }else if (vals_matrix_loc[i][j] == 1){
                            panel_img_obstacle[i][j].setVisibility(View.VISIBLE);
                            panel_img_obstacle[i][j].setImageResource(R.drawable.img_tom);
                            setVisibleFromTo(panel_img_player,playerPosition,playerPosition);
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
        for (int i = vals_matrix_loc.length - 1; i >= 0; i--) {
            for (int j = 0; j < vals_matrix_loc[i].length; j++){
                if(i > 0){
                    vals_matrix_loc[i][j] = vals_matrix_loc[i-1][j];
                }
            }
        }
    }
}
