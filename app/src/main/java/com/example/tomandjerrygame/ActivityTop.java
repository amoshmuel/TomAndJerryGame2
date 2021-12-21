package com.example.tomandjerrygame;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ActivityTop extends AppCompatActivity {

    private Bundle bundle;
    private FragListTop fragmentList;
    private FragMap fragmentMap;
    private RecordsDB recordDB;
    private String name;
    private String lat;
    private String lng;
    private int score;

    CallBackList callBackList = (index) -> {
        Record record = recordDB.getAllRecords().get(index);
        String playerName = record.getName();
        double lat = record.getLat();
        double lng = record.getLng();
        int score = record.getScore();
//        fragmentMap.setLocation(playerName, score, lat, lng);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        this.bundle = getIntent().getBundleExtra(Flags.BUNDLE);

        /* Top10List Fragment */
        fragmentList = new FragListTop();
        fragmentList.setCallBackList(callBackList);
        getSupportFragmentManager().beginTransaction().add(R.id.top_frame_name, fragmentMap).commit();
        updateRecordDB();


        /* GoogleMap Fragment */
        fragmentMap = new FragMap();
        fragmentMap.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.top_frame_map, fragmentMap).commit();

    }


    private void updateRecordDB() {
        recordDB = receiveRecordDBFromSP();
        unboxingBoundle();

        int score = bundle.getInt(Flags.SCORE, KeysAndValues.PLAYER_SCORE_DEFAULT);
        if (score == KeysAndValues.PLAYER_SCORE_DEFAULT) {
            refreshList();
            return;
        }

        GPS.getMe().getLocation((lat, lon) -> updateCurrentRecord(lat, lon, score));
    }

    private void unboxingBoundle() {
        bundle = getIntent().getExtras().getBundle(Flags.BUNDLE);
        name = bundle.getString(Flags.getNAME());
        lat = bundle.getDouble(Flags.getLAT());
        lng = bundle.getDouble(Flags.getLNG());
    }

    private RecordsDB receiveRecordDBFromSP() {
        RecordsDB recordDB = new Gson().fromJson(MSPV3.getMe().getString(Flags.DATABASE, null), RecordsDB.class);
        if (recordDB == null){
            recordDB = new RecordsDB();
        }
        return recordDB;
    }

    private void updateCurrentRecord(double lat, double lon, long score) {
        String playerName = bundle.getString(KeysAndValues.PLAYER_USERNAME_KEY);
        Record currentRecord = new Record().setPlayerName(playerName).setScore(score).setLat(lat).setLon(lon);
        RecordDBController.updateRecord(recordDB, currentRecord);
        refreshList();
    }

    private void refreshList() {
        fragmentList.setRecords(recordDB.getRecords());
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentList).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

}