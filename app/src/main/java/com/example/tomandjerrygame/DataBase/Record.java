package com.example.tomandjerrygame.DataBase;

public class Record implements Comparable<Record>{

    private String name;
    private int score;
    private double lat;
    private double lng;

    public Record(String name, int score, double latitude, double longitude) {
        this.name = name;
        this.score = score;
        this.lat = latitude;
        this.lng = longitude;
    }

    public String getName() {
        return name;
    }


    public int getScore() {
        return score;
    }


    public double getLat() {
        return lat;
    }



    public double getLng() {
        return lng;
    }


    @Override
    public int compareTo(Record rec) {
        if(this.score > rec.getScore())
            return -1;
        else if (this.score < rec.getScore())
            return 1;
        return 0;
    }
}
