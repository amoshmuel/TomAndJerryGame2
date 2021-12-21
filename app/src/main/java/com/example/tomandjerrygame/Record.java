package com.example.tomandjerrygame;

public class Record implements Comparable<Record>{

    private String name;
    private int score;
    private double lat;
    private double lng;

    public Record() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int compareTo(Record data) {
        return data.getScore();
    }
}
