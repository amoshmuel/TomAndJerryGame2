package com.example.tomandjerrygame;

import androidx.appcompat.app.AppCompatActivity;

public interface GameController {

    void setActivity(AppCompatActivity activity);

    void setCallBackMovePlayer(CallBackMovePlayer cb);
}
