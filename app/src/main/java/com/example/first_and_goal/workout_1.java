package com.example.first_and_goal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class workout_1 extends Fragment {

    private TextView workout1;
    private FloatingActionButton addWorkout;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<workout_upload> mUploads;
    private ProgressBar progressBar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference workref = db.collection(user.getEmail()).document("Workouts");
    private DocumentReference workoutref;
    private static final String KEY_WORK_1 = "Workout 1";

    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Workout").child("1");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_workout_1, container, false);


        workout1 = RootView.findViewById(R.id.workout1);
        addWorkout = RootView.findViewById(R.id.addWorkout);
        recyclerView = RootView.findViewById(R.id.workout_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = RootView.findViewById(R.id.progressBar);

        mUploads = new ArrayList<>();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    workout_upload upload = postSnapshot.getValue(workout_upload.class);
                    mUploads.add(upload);
                }
                imageAdapter = new ImageAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(imageAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });




        workref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String work1 = documentSnapshot.getString(KEY_WORK_1);
                    if (work1 != null) {
                        workout1.setText(work1);
                    }
                } else {
                    Toast.makeText(getActivity(), "No such thing", Toast.LENGTH_SHORT).show();
                }

            }
        });


        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                final LinearLayout layout = new LinearLayout(context);
                final EditText input = new EditText(getActivity());
                final EditText reps = new EditText(getActivity());
                final EditText sets = new EditText(getActivity());
                input.setHint("New workout");
                reps.setHint("Reps");
                sets.setHint("Sets");
                layout.addView(input);
                layout.addView(sets);
                layout.addView(reps);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Set workout name");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        workout_upload upload = new workout_upload(input.getText().toString().trim(), sets.getText().toString().trim(),
                                reps.getText().toString().trim());


                        dbref.child(input.getText().toString()).setValue(upload);


                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.create().cancel();
                    }
                });

                dialog.setCancelable(true);
                dialog.create().show();
            }
        });


        return RootView;
    }


}
