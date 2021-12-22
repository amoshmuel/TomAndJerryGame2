package com.example.tomandjerrygame.DataBase;

import com.example.tomandjerrygame.Flags.Flags;

import java.util.ArrayList;
import java.util.Collections;

public class RecordsDB {

    private ArrayList<Record> records;

    public RecordsDB() {
        records = new ArrayList<Record>();
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public RecordsDB setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public void addRecord(Record rec){
        this.records.add(rec);
        Collections.sort(this.records);
        if(this.records.size()> Flags.MAXSIZE){
            this.records.remove(Flags.MAXSIZE);
        }
    }
}


