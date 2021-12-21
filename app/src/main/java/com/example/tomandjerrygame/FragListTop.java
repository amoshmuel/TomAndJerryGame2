package com.example.tomandjerrygame;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import java.util.ArrayList;
public class FragListTop extends Fragment {

    private CallBackList callBackList;
    private TextView[] names;
    private TextView[] scores;

    public void setCallBackList(CallBackList callBackList) {
        this.callBackList = callBackList;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_list_top, container, false);
        findViews(view);
        initViews();
        return view;
    }


    private void initViews() {
        String js = MSPV3.getMe().getString(Flags.DATABASE, "");
        RecordsDB db = new Gson().fromJson(js, RecordsDB.class);
        ArrayList<Record> records = db.getAllRecords();
        for (int i = 0; i < records.size(); i++) {
            Record oneRec = records.get(i);
            names[i].setText(i+1 + ": " + oneRec.getName());
            scores[i].setText(oneRec.getScore());
        }
    }

    private void findViews(View view) {
        names = new TextView[]{
                view.findViewById(R.id.panel_txt_name1),
                view.findViewById(R.id.panel_txt_name2),
                view.findViewById(R.id.panel_txt_name3),
                view.findViewById(R.id.panel_txt_name4),
                view.findViewById(R.id.panel_txt_name5),
                view.findViewById(R.id.panel_txt_name6),
                view.findViewById(R.id.panel_txt_name7),
                view.findViewById(R.id.panel_txt_name8),
                view.findViewById(R.id.panel_txt_name9),
                view.findViewById(R.id.panel_txt_name10),
        };

        scores = new TextView[]{
                view.findViewById(R.id.panel_txt_score1),
                view.findViewById(R.id.panel_txt_score2),
                view.findViewById(R.id.panel_txt_score3),
                view.findViewById(R.id.panel_txt_score4),
                view.findViewById(R.id.panel_txt_score5),
                view.findViewById(R.id.panel_txt_score6),
                view.findViewById(R.id.panel_txt_score7),
                view.findViewById(R.id.panel_txt_score8),
                view.findViewById(R.id.panel_txt_score9),
                view.findViewById(R.id.panel_txt_score10),

        };
    }
}