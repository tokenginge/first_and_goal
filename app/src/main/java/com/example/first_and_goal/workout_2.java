package com.example.first_and_goal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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


public class workout_2 extends Fragment implements ImageAdapter.OnMenuItemClickListener {

    private TextView workout1;
    private FloatingActionButton addWorkout;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<workout_upload> mUploads;
    private ProgressBar progressBar;
    private Button start, pause, save, back;
    private Chronometer chronometer;
    private boolean running;
    private long pauseoffSet;
    private CoordinatorLayout coordinatorLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference workref = db.collection(user.getEmail()).document("Workouts");
    private DocumentReference workoutref = db.collection(user.getEmail()).document("Workouts").collection("number").document("2");
    private static final String KEY_WORK_1 = "Workout 2";
    private static final String KEY_RATE = "Rating";
    private static final String KEY_TIME = "Time";
    private static final String KEY_NOTES = "Notes";
    private static final String KEY_NUM = "Workout number";
    private  Map<String, Object> note = new HashMap<>();
    private ValueEventListener mDBListener;

    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Workout").child("2");
    private DatabaseReference mData = db2.getReference(user.getUid()).child("Workout");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_workout_2, container, false);

        workout1 = RootView.findViewById(R.id.workout1);
        addWorkout = RootView.findViewById(R.id.addWorkout);
        recyclerView = RootView.findViewById(R.id.workout_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = RootView.findViewById(R.id.progressBar);
        start = RootView.findViewById(R.id.btn_start);
        pause = RootView.findViewById(R.id.btn_pause);
        save = RootView.findViewById(R.id.btn_save);
        chronometer = RootView.findViewById(R.id.chronometer);
        chronometer.setFormat("%ss");
        chronometer.setBase(SystemClock.elapsedRealtime());
        coordinatorLayout = RootView.findViewById(R.id.coordinator);
        back = RootView.findViewById(R.id.btn_back);
        save.setVisibility(View.INVISIBLE);




        mUploads = new ArrayList<>();







        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workout newfrag2 = new workout();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();
            }
        });





        mDBListener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    workout_upload upload = postSnapshot.getValue(workout_upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                imageAdapter = new ImageAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(imageAdapter);
                imageAdapter.setOnMenuItemClickListener(workout_2.this);
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
                    workoutref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                String past_time = documentSnapshot.getString(KEY_TIME);
                                String past_rating = documentSnapshot.getString(KEY_RATE);
                                float mPas = Float.parseFloat(past_rating);
                                String past_note = documentSnapshot.getString(KEY_NOTES);
                                String past_num = documentSnapshot.getString(KEY_NUM);
                                Context context = getContext();
                                final LinearLayout layout = new LinearLayout(context);
                                final TextView tv1 = new TextView(getContext());
                                final TextView tv2 = new TextView(getContext());
                                final TextView tv3 = new TextView(getContext());
                                final AlertDialog.Builder past = new AlertDialog.Builder(getActivity());
                                past.setTitle("Welcome Back!");
                                if (past_num.equals("1")){
                                    tv1.setText("Last time you completed " + past_num + " exercise in " + past_time + " . Try and aim for more this time!" );
                                }else if (past_num.equals("0") || past_num.equals("2") || past_num.equals("3") || past_num.equals("4")){
                                    tv1.setText("Last time you completed " + past_num + " exercises in " + past_time + " . Try and aim for more this time!" );
                                }
                                else {

                                    tv1.setText("Last time you completed " + past_num + " exercise in " + past_time + " . Well done!" );
                                }

                                if(mPas == 2.5 || mPas == 3.0  || mPas == 3.5|| mPas == 4.0  || mPas == 4.5 || mPas == 5.0 ){
                                    tv2.setText("Rating: " + past_rating + "/5. " + "Keep up the good work!");
                                }else {
                                    tv2.setText("Rating: " + past_rating + "/5. " + "Try to improve your workout this time!");
                                }
                                if(past_note.equals("")){
                                    tv3.setText("Nothing noted");
                                }else{
                                    tv3.setText("Notes: " + past_note);
                                }

                                layout.addView(tv1);
                                layout.addView(tv2);
                                layout.addView(tv3);
                                layout.setOrientation(LinearLayout.VERTICAL);
                                layout.setPadding(8,8, 0, 0);
                                past.setView(layout);
                                past.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        past.create().cancel();
                                    }
                                });
                                past.setCancelable(true);
                                past.create().show();

                            }
                        }

                    });

                } else {
                    Toast.makeText(getActivity(), "Not yet completed", Toast.LENGTH_SHORT).show();
                }

            }
        });


        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                final LinearLayout layout = new LinearLayout(context);
                final EditText input = new EditText(getContext());
                final EditText reps = new EditText(getContext());
                final EditText sets = new EditText(getContext());
                input.setHint("New workout");
                reps.setHint("Reps");
                sets.setHint("Sets");
                sets.setInputType(InputType.TYPE_CLASS_NUMBER);
                reps.setInputType(InputType.TYPE_CLASS_NUMBER);
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


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                if (!running){
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffSet);
                    chronometer.start();
                    running = true;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    chronometer.stop();
                    pauseoffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Paused");
                dialog.setNeutralButton("Resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!running){
                            chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffSet);
                            chronometer.start();
                            running = true;
                        }


                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        pause.setVisibility(View.GONE);
                        start.setVisibility(View.VISIBLE);

                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();




            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    chronometer.stop();
                    pauseoffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Do you want to save?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        pause.setVisibility(View.GONE);
                        start.setVisibility(View.VISIBLE);
                        Context context = getContext();
                        final LinearLayout layout = new LinearLayout(context);
                        final RatingBar rate = new RatingBar(getContext());
                        final EditText work_num  = new EditText(getContext());
                        final EditText notes = new EditText(getContext());




                        final AlertDialog.Builder msave = new AlertDialog.Builder(getActivity());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        work_num.setHint("Number of workouts completed");
                        notes.setHint("Notes");
                        msave.setTitle("Save");
                        rate.setMinimumWidth(2);
                        work_num.setInputType(InputType.TYPE_CLASS_NUMBER);
                        layout.addView(work_num);
                        layout.addView(rate);
                        layout.addView(notes);
                        msave.setView(layout);
                        msave.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String time = chronometer.getText().toString().trim();
                                String mRate = String.valueOf(rate.getRating());
                                String mWork = work_num.getText().toString().trim();
                                String mNote = notes.getText().toString().trim();
                                note.put(KEY_NUM, mWork);
                                note.put(KEY_RATE, mRate);
                                note.put(KEY_NOTES, mNote);
                                note.put(KEY_TIME, time);
                                save.setVisibility(View.INVISIBLE);


                                workref.collection("number").document("2").set(note);
                                showSnackbar();
                                chronometer.setBase(SystemClock.elapsedRealtime());
                                pauseoffSet = 0;
                            }
                        });
                        msave.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.create().show();
                            }
                        });
                        msave.create().show();
                        msave.setCancelable(false);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        pauseoffSet = 0;
                        chronometer.stop();
                        pause.setVisibility(View.GONE);
                        start.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Not saved", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!running){
                            chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffSet);
                            chronometer.start();
                            running = true;
                        }


                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();

            }
        });





        return RootView;

    }



    public void showSnackbar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Saved!", Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }




    public void onDeleteClick(int position){
        workout_upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        dbref.child(selectedKey).removeValue();
        Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();

    }


    public void onBackPressed(){

        workout newfrag2 = new workout();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();
    }

}








