package com.example.tomandjerrygame;

import java.util.ArrayList;
import java.util.Collections;

public class RecordsDB {

    private ArrayList<Record> data;
    private final int MAXSIZE = 10;

    public RecordsDB() {
        data = new ArrayList<Record>();
    }

    public ArrayList<Record> getAllRecords() {
        return data;
    }

    public void setAllRecords(ArrayList<Record> allData) {
        this.data = allData;
    }

    public void addRecord(Record oneData){
        this.data.add(oneData);
        Collections.sort(this.data);
        if(this.data.size()>MAXSIZE){
            this.data.remove(MAXSIZE);
        }
    }
}


