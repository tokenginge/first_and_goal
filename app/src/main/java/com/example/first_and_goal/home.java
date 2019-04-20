package com.example.first_and_goal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.ResourceBundle;


public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
  //  private Profile fragmentA;
  //  private profile2 fragmentB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.myDrawer);
        NavigationView navigationView = findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new home_frag()).commit();
            navigationView.setCheckedItem(R.id.home);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new home_frag()).commit();
                break;

           case R.id.prof:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new Profile()).commit();
                break;

            case R.id.set:
                startActivity(new Intent(home.this, Settings.class));
                break;

            case R.id.step:
                startActivity(new Intent(home.this, Running.class));
                break;

            case R.id.work:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new workout()).commit();
                break;

            case R.id.drl:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new Drills()).commit();
                break;

            case R.id.ques:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new FAQ()).commit();
                break;

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, "Cannot exit with back button", Toast.LENGTH_SHORT).show();

        }
    }
}



