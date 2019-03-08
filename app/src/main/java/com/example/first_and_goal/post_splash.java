package com.example.first_and_goal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class post_splash extends AppCompatActivity {

    private VideoView videoBack;
    MediaPlayer mMedia;
    int mCurrentVideoPosition;
    private Button btnSign;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_splash);

        btnSign = findViewById(R.id.logbtn);
        login = findViewById(R.id.text_log);
        videoBack = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.back);

        videoBack.setVideoURI(uri);
        videoBack.start();


        videoBack.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMedia = mediaPlayer;
                mMedia.setVolume(0f, 0f);
                mMedia.setLooping(true);
                if (mCurrentVideoPosition != 0){
                    mMedia.seekTo(mCurrentVideoPosition);
                    mMedia.start();
                }
            }
        });
btnSign.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(post_splash.this, signup.class);
        startActivity(intent);
    }
});

login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(post_splash.this, login.class);
        startActivity(intent);
    }
});
    }


    @Override
    protected void onResume(){
        super.onResume();
        videoBack.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mMedia.release();
        mMedia = null;
    }




}
