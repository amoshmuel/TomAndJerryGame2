package com.example.tomandjerrygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FragBtn extends Fragment implements GameController {


    private CallBackMovePlayer callBackMovePlayer;
    private ImageButton panel_BTN_left ;
    private ImageButton panel_BTN_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_btn, container, false);
        findButtonsView(view);
        initButtons();

        return view;
    }

    private void findButtonsView(View view) {
        panel_BTN_left      = view.findViewById(R.id.panel_btn_left );
        panel_BTN_right     = view.findViewById(R.id.panel_btn_right);
    }

    private void initButtons() {
        panel_BTN_left.setOnClickListener(v -> callBackMovePlayer.movePlayer(-1));

        panel_BTN_right.setOnClickListener(v -> callBackMovePlayer.movePlayer(1));
    }

    @Override
    public void setActivity(AppCompatActivity activity) {
    }

    @Override
    public void setCallBackMovePlayer(CallBackMovePlayer callBackMovePlayer) {
        this.callBackMovePlayer = callBackMovePlayer;
    }
}