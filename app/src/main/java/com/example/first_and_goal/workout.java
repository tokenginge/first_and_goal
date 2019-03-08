package com.example.first_and_goal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;


public class workout extends Fragment {

    private TextView workout1, workout2, workout3, workout4, workout5;
    private ImageView btn_work1, btn_work2, btn_work3, btn_work4, btn_work5;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference workref = db.collection(user.getEmail()).document("Workouts");
    private static final String KEY_WORK_1 = "Workout 1";
    private static final String KEY_WORK_2 = "Workout 2";
    private static final String KEY_WORK_3 = "Workout 3";
    private static final String KEY_WORK_4 = "Workout 4";
    private static final String KEY_WORK_5 = "Workout 5";
    private  Map<String, Object> note = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_workout, container, false);

        workout1 = RootView.findViewById(R.id.workout1);
        btn_work1 = RootView.findViewById(R.id.btn_workout_1);
        workout2 = RootView.findViewById(R.id.workout2);
        btn_work2 = RootView.findViewById(R.id.btn_workout_2);
        workout3 = RootView.findViewById(R.id.workout3);
        btn_work3 = RootView.findViewById(R.id.btn_workout_3);
        workout4 = RootView.findViewById(R.id.workout4);
        btn_work4 = RootView.findViewById(R.id.btn_workout_4);
        workout5 = RootView.findViewById(R.id.workout5);
        btn_work5 = RootView.findViewById(R.id.btn_workout_5);





        workout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setHint("New workout name");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("New workout name:");
                dialog.setView(input);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String work1 = input.getText().toString();
                        note.put(KEY_WORK_1, work1);
                        workout1.setText(work1);
                        workref.update(note);
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


        workout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setHint("New workout name");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("New workout name:");
                dialog.setView(input);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String work2 = input.getText().toString();
                      workref.update(KEY_WORK_2, work2);
                        workout2.setText(work2);
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

        workout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setHint("New workout name");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("New workout name:");
                dialog.setView(input);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String work3 = input.getText().toString();
                        workref.update(KEY_WORK_3, work3);
                        workout3.setText(work3);
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

        workout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setHint("New workout name");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("New workout name:");
                dialog.setView(input);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String work4 = input.getText().toString();
                        workref.update(KEY_WORK_4, work4);
                        workout4.setText(work4);
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

        workout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setHint("New workout name");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("New workout name:");
                dialog.setView(input);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String work5 = input.getText().toString();
                        workref.update(KEY_WORK_5, work5);
                        workout5.setText(work5);
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



        workref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String work1 = documentSnapshot.getString(KEY_WORK_1);
                    String work2 = documentSnapshot.getString(KEY_WORK_2);
                    String work3 = documentSnapshot.getString(KEY_WORK_3);
                    String work4 = documentSnapshot.getString(KEY_WORK_4);
                    String work5 = documentSnapshot.getString(KEY_WORK_5);

                    if (work1 != null) {
                        workout1.setText(work1);
                    } else {
                        Toast.makeText(getActivity(), "No workout 1 set yet!", Toast.LENGTH_SHORT).show();
                    }
                    if (work2 != null) {
                        workout2.setText(work2);
                    } else {
                        Toast.makeText(getActivity(), "No workout 2 set yet!", Toast.LENGTH_SHORT).show();
                    }
                    if (work3 != null){
                        workout3.setText(work3);
                    }else {
                        Toast.makeText(getActivity(), "No workout 3 set yet!", Toast.LENGTH_SHORT).show();
                    }
                    if (work4 != null){
                        workout4.setText(work4);
                    }else {
                        Toast.makeText(getActivity(), "No workout 4 set yet!", Toast.LENGTH_SHORT).show();
                    }
                    if (work5 != null){
                        workout5.setText(work5);
                    }else {
                        Toast.makeText(getActivity(), "No workout 5 set yet!", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        btn_work1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workout_1 newfrag2 = new workout_1();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();

            }
        });

        btn_work2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workout_2 newfrag2 = new workout_2();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();
            }
        });

        return RootView;
    }
}
