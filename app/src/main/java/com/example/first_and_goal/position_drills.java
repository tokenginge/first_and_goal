package com.example.first_and_goal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class position_drills extends Fragment {

    private Button OL_drills, dl_drills, db_drills, lb_drills, qb_drills, rb_drills, te_drills, wr_drills, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_position_drills, container, false);

        OL_drills = RootView.findViewById(R.id.btn_OL);
        dl_drills = RootView.findViewById(R.id.btn_DL);
        db_drills = RootView.findViewById(R.id.btn_DB);
        lb_drills = RootView.findViewById(R.id.btn_LBS);
        qb_drills = RootView.findViewById(R.id.btn_QBs);
        rb_drills = RootView.findViewById(R.id.btn_Run_backs);
        te_drills = RootView.findViewById(R.id.btn_TE);
        wr_drills = RootView.findViewById(R.id.btn_wide_rec);
        back = RootView.findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drills newfrag2 = new Drills();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();
            }
        });


        OL_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OL_Drills.class));
            }
        });


        dl_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DL_drills.class));

            }
        });

        lb_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LB_Drills.class));
            }
        });

        db_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DB_Drills.class));
            }
        });

        qb_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), QB_Drills.class));
            }
        });

        rb_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RB_Drills.class));
            }
        });

        te_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TE_Drills.class));
            }
        });

        wr_drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WR_Drills.class));
            }
        });


        return RootView;
    }
}