package com.example.first_and_goal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;





public class Drills extends Fragment {

    private Button Speed, pos, warm;

    public Drills(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_drills, container, false);


        Speed =  RootView.findViewById(R.id.btn_Speed);
        warm = RootView.findViewById(R.id.btn_warm_up);
        pos =  RootView.findViewById(R.id.btn_Position);


        Speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speed_drills newfrag = new Speed_drills();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag, null).commit();

            }
        });

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position_drills newfrag2 = new position_drills();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();


            }
        });

        return RootView;
    }
}
