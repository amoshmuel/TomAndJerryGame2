package com.example.tomandjerrygame.Frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tomandjerrygame.CallBack.CallBackGameController;
import com.example.tomandjerrygame.CallBack.CallBackMovePlayer;
import com.example.tomandjerrygame.Flags.Flags;
import com.example.tomandjerrygame.R;

public class FragBtn extends Fragment implements CallBackGameController {


    private CallBackMovePlayer callBackMovePlayer;
    private ImageButton panelBtnLeft;
    private ImageButton panelBtnRight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_btn, container, false);
        findButtonsView(view);
        initButtons();

        return view;
    }

    private void findButtonsView(View view) {
        panelBtnLeft = view.findViewById(R.id.panel_btn_left );
        panelBtnRight = view.findViewById(R.id.panel_btn_right);
    }

    private void initButtons() {
        panelBtnLeft.setOnClickListener(v -> callBackMovePlayer.whereToMovePlayer(Flags.LEFT));

        panelBtnRight.setOnClickListener(v -> callBackMovePlayer.whereToMovePlayer(Flags.RIGHT));
    }

    @Override
    public void setActivity(AppCompatActivity activity) {
    }

    @Override
    public void setCallBackMovePlayer(CallBackMovePlayer callBackMovePlayer) {
        this.callBackMovePlayer = callBackMovePlayer;
    }
}