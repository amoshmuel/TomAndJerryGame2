package com.example.tomandjerrygame;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class ActivityMain extends AppCompatActivity {
    private ImageView[][] panel_img_obstacle;
    private int[][] vals_matrix_loc;
    private ImageView [] panel_img_player;
    private ImageView [] panel_img_live;
    private int  playerPosition =2;
    private int delay = 1000;
    private final Handler handler = new Handler();
    private Runnable timerRunnable;
    private final int VIBRATE_TIME = 500;
    private int counter = 0;
    private boolean flag = true;
    private ImageView panel_img_background;
    private TextView duaration;
    private int duarationCounter = 0;
    private MediaPlayer moveSound;
    private MediaPlayer crashSound;
    private MediaPlayer coinSound;
    private boolean flagReg = true;

    private Bundle bundle;
    private Fragment fr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unboxingBoundle();

        CallBackMovePlayer callBackMvP = new CallBackMovePlayer() {
            @Override
            public void movePlayer(int direction) {
                setVisibleFromTo(panel_img_player,playerPosition,playerPosition+direction);
            }
            @Override
            public void delayGame(int delay) {
                ActivityMain.this.delay = delay;
            }
        };

        sensorOrBtn(callBackMvP);
        findViews();
        moveSound.start();
        initialMatrix();

        timerRunnable = () -> {
            if(flag == true){
                updateClockView();
                handler.postDelayed(timerRunnable, delay);
            }else{
                stopTicker();
                cleanUI();
                bundle.putLong(Flags.SCORE, duarationCounter);
                Intent myIntent = new Intent(this, ActivityTop.class);
                myIntent.putExtra(Flags.BUNDLE, bundle);
                startActivity(myIntent);
            }
        };
    }

    private void sensorOrBtn(CallBackMovePlayer callBackMvP) {
        if (flagReg){
            whichFrag(new FragBtn(),callBackMvP);
        }else{
            whichFrag(new FragAcc(),callBackMvP);
        }
    }

    private void whichFrag(GameController frag, CallBackMovePlayer cb) {
        frag.setActivity(this);
        frag.setCallBackMovePlayer(cb);
        getSupportFragmentManager().beginTransaction().add(R.id.panel_frm_controller, (Fragment) frag).commit();
    }

    private void unboxingBoundle() {
        bundle = getIntent().getExtras().getBundle(Flags.BUNDLE);
        flagReg = bundle.getBoolean(Flags.getFLAGREG());
    }

    private void initialMatrix() {
//        initial the matrix to 0
        for (int i = 0; i < vals_matrix_loc.length; i++) {
            for (int j = 0; j < vals_matrix_loc[i].length; j++) {
                vals_matrix_loc[i][j] = 0;
            }
        }
    }

    private void cleanUI() {
//        clear the UI before finish the game
        for (int i = 0; i < panel_img_obstacle.length; i++) {
            for (int j = 0; j < panel_img_obstacle[i].length; j++) {
                if(panel_img_obstacle[i][j].getVisibility() == View.VISIBLE)
                    panel_img_obstacle[i][j].setVisibility(View.INVISIBLE);
            }
        }
        panel_img_player[playerPosition].setVisibility(View.INVISIBLE);
    }

    private void setVisibleFromTo(ImageView [] player ,int from, int to) {
//        move the player from current location to new location
        if (to>4 || to < 0)
            to = from;
        if(to != from){
            player[from].setVisibility(View.INVISIBLE);
        }
        player[to].setImageResource(R.drawable.img_jerry);
        player[to].setVisibility(View.VISIBLE);
        playerPosition = to;
    }


    private void findViews() {
        panel_img_background = findViewById(R.id.img_back);
        panel_img_obstacle = new ImageView[][]{
                {findViewById(R.id.panel_img_tom_00), findViewById(R.id.panel_img_tom_01), findViewById(R.id.panel_img_tom_02),findViewById(R.id.panel_img_tom_03),findViewById(R.id.panel_img_tom_04)},
                {findViewById(R.id.panel_img_tom_10), findViewById(R.id.panel_img_tom_11), findViewById(R.id.panel_img_tom_12),findViewById(R.id.panel_img_tom_13),findViewById(R.id.panel_img_tom_14)},
                {findViewById(R.id.panel_img_tom_20), findViewById(R.id.panel_img_tom_21), findViewById(R.id.panel_img_tom_22),findViewById(R.id.panel_img_tom_23),findViewById(R.id.panel_img_tom_24)},
                {findViewById(R.id.panel_img_tom_30), findViewById(R.id.panel_img_tom_31), findViewById(R.id.panel_img_tom_32),findViewById(R.id.panel_img_tom_33),findViewById(R.id.panel_img_tom_34)},
                {findViewById(R.id.panel_img_tom_40), findViewById(R.id.panel_img_tom_41), findViewById(R.id.panel_img_tom_42),findViewById(R.id.panel_img_tom_43),findViewById(R.id.panel_img_tom_44)},
                {findViewById(R.id.panel_img_jerry0), findViewById(R.id.panel_img_jerry1), findViewById(R.id.panel_img_jerry2),findViewById(R.id.panel_img_jerry3),findViewById(R.id.panel_img_jerry4)}
        };
        vals_matrix_loc = new int[panel_img_obstacle.length][panel_img_obstacle[0].length];
        panel_img_player = new ImageView[]{
                findViewById(R.id.panel_img_jerry0),
                findViewById(R.id.panel_img_jerry1),
                findViewById(R.id.panel_img_jerry2),
                findViewById(R.id.panel_img_jerry3),
                findViewById(R.id.panel_img_jerry4)
        };
        panel_img_live = new ImageView[]{
                findViewById(R.id.panel_img_chesse1),
                findViewById(R.id.panel_img_chesse2),
                findViewById(R.id.panel_img_chesse3)
        };

        duaration = findViewById(R.id.panel_txt_duaration);
        moveSound =  MediaPlayer.create(this,R.raw.move_sound);
        crashSound = MediaPlayer.create(this,R.raw.crash);
        coinSound = MediaPlayer.create(this,R.raw.coin);

    }


    private void checkCrash() {
        //        check if was an object on the player postion.
        //        and remove the last cheese-live from the array.
        if(vals_matrix_loc[vals_matrix_loc.length- 1][playerPosition] == 1 ){
            duarationCounter -=250;
            for (int j = panel_img_live.length - 1; j >= 0; j--) {
                if(panel_img_live[j].getVisibility() == View.VISIBLE){
                    panel_img_live[j].setVisibility(View.INVISIBLE);
//                    panel_img_player[playerPosition].setImageResource(R.drawable.img_jerry_angry);
//                    panel_img_player[playerPosition].setVisibility(View.VISIBLE);
                    crashSound.start();
                    if(j != 0 ){
                        Toast.makeText(ActivityMain.this, "HIT!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ActivityMain.this, "HIT!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ActivityMain.this, "Game Over", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                    vibrate(VIBRATE_TIME);
                    return;
                }
            }
        }else if (vals_matrix_loc[vals_matrix_loc.length- 1][playerPosition] == 2 ){
            coinSound.start();
            duarationCounter+=300;
            Toast.makeText(ActivityMain.this, "EXCELLENT", Toast.LENGTH_SHORT).show();
            vibrate(VIBRATE_TIME);
            return;
        }
    }

    private void vibrate(int timer) {
//        making vibrate after hit
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATE_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(timer);
        }
    }


    private void runlogic() {
//        every 2 row initial the first row with only 1 number and the other row is initial with 0.
//        the location of the 1 in the row is random. in the matrix an the other is 0 (the num 1 will swap to an obstacle)
        if(counter++ %2 == 0){
            Random r = new Random();
            int obstacleRandom = r.nextInt(vals_matrix_loc[0].length);
            vals_matrix_loc[0][obstacleRandom] =1 ;
            int coinRandom = r.nextInt(vals_matrix_loc[0].length-1);
            if (coinRandom >= obstacleRandom) {
                coinRandom += 1;
            }
            vals_matrix_loc[0][coinRandom] =2;
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
//                every '1' in the matrix swap to an obstacl and make sure the player is in the place
                for (int i = 0; i < panel_img_obstacle.length; i++) {
                    for (int j = 0; j < panel_img_obstacle[i].length; j++) {
                        if(vals_matrix_loc[i][j] == 0){
                            panel_img_obstacle[i][j].setVisibility(View.INVISIBLE);
//                            setVisibleFromTo(panel_img_player,playerPosition,playerPosition);
                        }else if (vals_matrix_loc[i][j] == 1){
                            panel_img_obstacle[i][j].setVisibility(View.VISIBLE);
                            panel_img_obstacle[i][j].setImageResource(R.drawable.img_tom);
//                            setVisibleFromTo(panel_img_player,playerPosition,playerPosition);
                        }else if (vals_matrix_loc[i][j] == 2){
                            panel_img_obstacle[i][j].setImageResource(R.drawable.coin_img);
                            panel_img_obstacle[i][j].setVisibility(View.VISIBLE);
//                            setVisibleFromTo(panel_img_player,playerPosition,playerPosition);
                        }
                        setVisibleFromTo(panel_img_player,playerPosition,playerPosition);
                    }
                }
            }
        });
    }

    private void startTicker() {
        handler.postDelayed(timerRunnable, delay);
    }

    private void updateClockView() {
//        every 2 sec check if was a crash, initial random 1 in the first row
//        swap the 1 to an obstacle and move down the obstacle.
        checkCrash();
        runlogic();
        updateUI();
        moveForward();

    }

    private void moveForward() {
//        move the obstacle down
        duarationCounter+=100;
        duaration.setText(""+ duarationCounter);
        for (int i = vals_matrix_loc.length - 1; i >= 0; i--) {
            for (int j = 0; j < vals_matrix_loc[i].length; j++){
                if(i > 0){
                    vals_matrix_loc[i][j] = vals_matrix_loc[i-1][j];
                }
            }
        }
    }
}
