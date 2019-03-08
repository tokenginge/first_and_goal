package com.example.first_and_goal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Speed_drills extends Fragment {

    private Button btn_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_speed_drills, container, false);

        btn_back = (Button) RootView.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drills newfrag = new Drills();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag, null).commit();
                btn_back.setVisibility(View.GONE);
            }
        });


        return RootView;
    }


}