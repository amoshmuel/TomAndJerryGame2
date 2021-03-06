package com.example.tomandjerrygame.Frag;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tomandjerrygame.CallBack.CallBackGameController;
import com.example.tomandjerrygame.CallBack.CallBackMovePlayer;
import com.example.tomandjerrygame.Flags.Flags;
import com.example.tomandjerrygame.R;


public class FragAcc extends Fragment implements CallBackGameController {
    private int delay = 1000;
    private AppCompatActivity activity;
    private CallBackMovePlayer callBackMovePlayer;
    private SensorManager sensorManager;
    private Sensor accSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_acc, container, false);
        initSensor();
        return view;
    }


    private void initSensor() {
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private final SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            if (x < -4) {
                callBackMovePlayer.whereToMovePlayer(Flags.RIGHT);
            } else if (x > 4) {
                callBackMovePlayer.whereToMovePlayer(Flags.LEFT);
            }


            if (y < -4) {
                if (delay > Flags.FASTERPEED)
                    delay -= Flags.CHANGESPEED;
                callBackMovePlayer.updateDelayGame(delay);
            } else if (y > 4) {
                if (delay < Flags.SLOWERSPEED)
                    delay += Flags.CHANGESPEED;
                callBackMovePlayer.updateDelayGame(delay);
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensorEventListener);
    }


    @Override
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void setCallBackMovePlayer(CallBackMovePlayer callBackMovePlayer) {
        this.callBackMovePlayer = callBackMovePlayer;
    }
}