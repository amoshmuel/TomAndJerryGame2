package com.example.tomandjerrygame;

import android.app.Application;

import com.google.gson.Gson;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSPV3.initHelper(this);
        String js = MSPV3.getMe().getString(Flags.DATABASE, "");
        RecordsDB database = new Gson().fromJson(js, RecordsDB.class);
        if(database==null){
            RecordsDB myDB = new RecordsDB();
            String json = new Gson().toJson(myDB);
            MSPV3.getMe().putString(Flags.DATABASE, json);
        }
    }
}
