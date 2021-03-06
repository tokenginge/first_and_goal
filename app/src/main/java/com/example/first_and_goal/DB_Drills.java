package com.example.first_and_goal;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Code adapted form coding in flow tutorial

public class DB_Drills extends AppCompatActivity {
    private RecyclerView mRecyler;
    private DrillItemsAdapter mAdapt;
    private ProgressBar progressBar;
    private DatabaseReference mDataRef;
    private List<Drillitems> mDrills;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db__drills);

        mRecyler = findViewById(R.id.recycler_view);
        mRecyler.setHasFixedSize(true);
        mRecyler.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar);

        mDrills = new ArrayList<>();

        mDataRef = FirebaseDatabase.getInstance().getReference("DB");

        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Drillitems drills = postSnapshot.getValue(Drillitems.class);
                    mDrills.add(drills);
                }
                mAdapt = new DrillItemsAdapter(DB_Drills.this, mDrills);

                mRecyler.setAdapter(mAdapt);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DB_Drills.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

}