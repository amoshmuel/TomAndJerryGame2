package com.example.tomandjerrygame;

public class Flags {
    public static final String FLAGREG = "FLAGREG";
    public static final String NAME = "NAME";
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";
    public static final int RIGHT = 1;
    public static final int LEFT = -1;
    public static final int CHANGESPEED = 250;
    public static final int FASTERPEED = 500;
    public static final int SLOWERSPEED = 1500;
    public static final String BUNDLE = "BUNDLE";
    public static final String DATABASE = "DATABASE";
    public static final String SCORE = "SCORE";


    public static String getFLAGREG() {
        return FLAGREG;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getLAT() {
        return LAT;
    }

    public static String getLNG() {
        return LNG;
    }
}
