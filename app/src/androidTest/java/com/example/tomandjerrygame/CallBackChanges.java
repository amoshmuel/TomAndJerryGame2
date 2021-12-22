package com.example.tomandjerrygame;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tomandjerrygame.CallBack.CallBackMovePlayer;

public interface CallBackChanges {

    void setActivity(AppCompatActivity activity);

    void setCallBackMovePlayer(CallBackMovePlayer cb);
}
