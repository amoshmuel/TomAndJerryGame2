package com.example.tomandjerrygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
public class FragBtn extends Fragment {
    private ImageButton btn_left;
    private ImageButton btn_right;
    private View fragView;
    private int  playerPosition =2;
    private int delay = 1000;
    private callBackplayerPos mcallBackplayerPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_frag__btn, container, false);
        findViews();
        initViews();

        return fragView;
    }

    private void initViews() {
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerPosition++;
                mcallBackplayerPos.playerPos(playerPosition);
            }
//                if (playerPosition == 0) {//player on the left move to left mid
//                    setVisibleFromTo(panel_img_player,0,1);
//
//                } else if (playerPosition == 1) {//player on the left mid move to mid
//                    setVisibleFromTo(panel_img_player,1,2);
//
//                } else if (playerPosition == 2) { //player on the mid move to right mid
//                    setVisibleFromTo(panel_img_player,2,3);
//
//                } else if (playerPosition == 3) { //player on the right mid move to right
//                    setVisibleFromTo(panel_img_player,3,4);
//
//                }else if (playerPosition == 4) { //player on the right move cant move
//                    setVisibleFromTo(panel_img_player,4,4);
//                }
//            }
        });
/*
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerPosition == 4) {//player on the right move to right mid
                    setVisibleFromTo(panel_img_player,4,3);

                } else if (playerPosition == 3) {//player on the right mid move to mid
                    setVisibleFromTo(panel_img_player,3,2);

                } else if (playerPosition == 2) { //player on the mid move to left mid
                    setVisibleFromTo(panel_img_player,2,1);
                }

                else if (playerPosition == 1) { //player on the  lrft mid move to left
                    setVisibleFromTo(panel_img_player,1,0);
                }

                else if (playerPosition == 0) { //player on the left cant move
                    setVisibleFromTo(panel_img_player,0,0);
                }
            }
        });
*/

    }

    private void findViews() {
        btn_left = fragView.findViewById(R.id.panel_btn_left);
        btn_right = fragView.findViewById(R.id.panel_btn_right);
    }
}