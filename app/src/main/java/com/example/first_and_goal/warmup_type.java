package com.example.first_and_goal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class warmup_type extends Fragment {

    private Button dyna, stat, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_warmup_type, container, false);

       dyna = RootView.findViewById(R.id.btn_Dyn);
       stat = RootView.findViewById(R.id.btn_Static);
        back = RootView.findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drills newfrag2 = new Drills();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();
            }
        });


        dyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), dynamic.class));
            }
        });


        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), stat.class));

            }
        });



        return RootView;
    }
}