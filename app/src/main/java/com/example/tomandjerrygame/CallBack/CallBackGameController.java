package com.example.tomandjerrygame.CallBack;

import androidx.appcompat.app.AppCompatActivity;

public interface CallBackGameController {

    void setActivity(AppCompatActivity activity);

    void setCallBackMovePlayer(CallBackMovePlayer cb);
}
