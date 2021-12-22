package com.example.tomandjerrygame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomandjerrygame.Flags.Flags;
import com.example.tomandjerrygame.DataBase.MSPV3;
import com.example.tomandjerrygame.R;
import com.example.tomandjerrygame.DataBase.Record;
import com.example.tomandjerrygame.DataBase.RecordsDB;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class ActivitySaveName extends AppCompatActivity {

    private MediaPlayer gameOver;
    private TextView saveNameTxtViewscore;
    private EditText saveNameEditTxtName;
    private Button saveNameBtnSave;
    private Button saveNameTryAgain;
    private int score;
    private RecordsDB database;
    private LocationManager locationManager;
    private Location gpsLoc;
    private Location networkLoc;
    private Bundle bundle;
    private Location finalLoc;
    private double lng;
    private double lat;
    private String country, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_name);
        findViews();
        gameOver.start();
        initButton();
        bundle = getIntent().getExtras().getBundle(Flags.BUNDLE);
        score =bundle.getInt(Flags.SCORE);
        saveNameTxtViewscore.setText(""+score);
        getLocation();
    }

    private String getName() {
        return saveNameEditTxtName.getText().toString();
    }

    private void getLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        try {

            gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            networkLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gpsLoc != null) {
            finalLoc = gpsLoc;
            lat = finalLoc.getLatitude();
            lng = finalLoc.getLongitude();
        }
        else if (networkLoc != null) {
            finalLoc = networkLoc;
            lat = finalLoc.getLatitude();
            lng = finalLoc.getLongitude();
        }
        else {
            lat = 0.0;
            lng = 0.0;
        }


        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        try {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size() > 0) {
                country = addresses.get(0).getCountryName();
                address = addresses.get(0).getAddressLine(0);
            }
            else {
                country = "Unknown";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initButton() {
        saveNameBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(saveNameEditTxtName.getText().toString())){
                    Toast.makeText(ActivitySaveName.this, "To Save Record Enter Name Or Try Again", Toast.LENGTH_LONG).show();
                }else{
                    String name = getName();
                    getLocation();
                    insertRecord(name,score, lat, lng);
                    Toast.makeText(ActivitySaveName.this, "Your Record Saved", Toast.LENGTH_SHORT).show();
                    Intent Intent = new Intent(ActivitySaveName.this, ActivityMenu.class);
                    startActivity(Intent);
                    ActivitySaveName.this.finish();
                }
            }
        });

        saveNameTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(ActivitySaveName.this, ActivityMenu.class);
                startActivity(Intent);
                ActivitySaveName.this.finish();
            }
        });
    }

    private void insertRecord(String name, int score, double latitude, double longitude) {
        String js = MSPV3.getMe().getString(Flags.DATABASE, "");
        database = new Gson().fromJson(js, RecordsDB.class);
        if (database == null)
            database = new RecordsDB();
        database.addRecord(new Record(name,score,latitude,longitude));
        String json = new Gson().toJson(database);
        MSPV3.getMe().putString(Flags.DATABASE, json);
    }

    private void findViews() {
        saveNameTxtViewscore = findViewById(R.id.save_txv_score);
        saveNameEditTxtName = findViewById(R.id.save_ET_name);
        saveNameBtnSave = findViewById(R.id.save_btn_saverec);
        saveNameTryAgain = findViewById(R.id.save_btn_tryagain);
        gameOver = MediaPlayer.create(this,R.raw.game_over);

    }
}