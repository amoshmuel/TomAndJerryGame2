package com.example.tomandjerrygame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Activity_Menu extends AppCompatActivity {
    private int flagReg = 1; //1 - regular , 0 - sens
    private double lat, lng;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static final String BUNDLE = "BUNDLE";
    private Bundle bundle;
    private EditText menu_ET_name;
    private MediaPlayer startGame;
    private Button menu_BTN_sensor;
    private Button menu_BTN_regular;
    private Button menu_BTN_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();
        playMusic();


        // Check location permission
        if (ActivityCompat.checkSelfPermission(Activity_Menu.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(Activity_Menu.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void playMusic() {
        startGame.start();
    }


    @SuppressLint("MissingPermission")
        private void getCurrentLocation() {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(Activity_Menu.this,
                                    Locale.getDefault());
                            List<Address> addressList = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            lat = addressList.get(0).getLatitude();
                            lng = addressList.get(0).getLongitude();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    private void initViews() {
        menu_BTN_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(menu_ET_name.getText().toString()))
                    Toast.makeText(Activity_Menu.this, "Must enter name", Toast.LENGTH_SHORT).show();
                else
                    startGame("sensor");

            }
        });

        menu_BTN_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(menu_ET_name.getText().toString()))
                    Toast.makeText(Activity_Menu.this, "Must enter name", Toast.LENGTH_SHORT).show();
                else
                    startGame("regular");

            }
        });

        menu_BTN_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("top");
            }
        });
    }

    private void findViews() {
        menu_BTN_sensor = findViewById(R.id.menu_BTN_sensor);
        menu_BTN_regular = findViewById(R.id.menu_BTN_regular);
        menu_BTN_top = findViewById(R.id.menu_BTN_top);
        startGame = MediaPlayer.create(this,R.raw.tom_and_jerry_tune);
        menu_ET_name = findViewById(R.id.menu_ET_name);

    }


    private void startGame(String sns) {
        startGame.stop();
        Intent myIntent = new Intent();
        Bundle bundle = initBundleData(sns);
        if(sns.equals("top")){
            myIntent = new Intent(this,Activity_Top.class);
        }else if(sns.equals("sensor") || sns.equals("regular")){
            myIntent = new Intent(this,MainActivity.class);
        }
        myIntent.putExtra("Bundle", bundle);
        startActivity(myIntent);
    }

    private Bundle initBundleData(String sns) {
        Bundle bundle = new Bundle();
        if(sns.equals("sensor"))
            flagReg = 0;
        bundle.putInt(MainActivity.FLAGREG , flagReg);
        bundle.putString(MainActivity.NAME , String.valueOf(menu_ET_name.getText()));
        bundle.putDouble(MainActivity.LAT , lat);
        bundle.putDouble(MainActivity.LAT , lng);
        return bundle;
    }


}
