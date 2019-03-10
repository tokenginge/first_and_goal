package com.example.first_and_goal;

import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class profile2 extends Fragment {
    private static final String TAG = "profile2";
    private static final String KEY_NAME = "Name";
    private static final String KEY_AGE ="Age";
    private static final String KEY_HEIGHT = "Height";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_TEAM = "Team";
    private static final String KEY_POSITION = "Position";
    private static final String KEY_TARGET_WEIGHT = "Target Weight";
    private static final String KEY_WEIGHT_DATE = "Date";



    private EditText mName;
    private EditText mAge, mHeight, mWeight, mTeam, mPosition, eWeight;
    private Button mSave, mBack,tWeight, bWeight;

    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference notref = db.collection(user.getEmail()).document("Profile");
    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Profile").child("Weight");
   private DocumentReference weightHis;
    private DocumentReference noteref2= db.collection(user.getEmail()).document("Profile").collection("Weight").document("Target Weight");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile2, container, false);


        mName = RootView.findViewById(R.id.edit_name);
        mAge = RootView.findViewById(R.id.edit_age);
        mSave = RootView.findViewById(R.id.save);
        mBack = RootView.findViewById(R.id.btn_back);
        mHeight = RootView.findViewById(R.id.edit_height);
        mWeight = RootView.findViewById(R.id.edit_weight);
        mTeam = RootView.findViewById(R.id.edit_team);
        mPosition = RootView.findViewById(R.id.edit_position);
        tWeight= RootView.findViewById(R.id.t_Weight);
        eWeight = RootView.findViewById(R.id.enter_target_weight);
        bWeight = RootView.findViewById(R.id.save_target_weight);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String age = mAge.getText().toString();
                String height = mHeight.getText().toString();
                String weight = mWeight.getText().toString();
                String team = mTeam.getText().toString();
                String position = mPosition.getText().toString();
                Date date = new SimpleDateFormat().get2DigitYearStart();


                if (weight.equals("")){
                    Toast.makeText(getActivity(), "No weight entered", Toast.LENGTH_SHORT).show();
                }
                else  {
                    int x = (int) new Date().getTime();
                    int y = Integer.parseInt(weight);

                    PointValue pointValue = new PointValue(x, y);

                    String id = dbref.push().getKey();


                    dbref.child(id).setValue(pointValue);
                }




                Map<String, Object> note = new HashMap<>();
                Map<String, Object> note_weight = new HashMap<>();
                Map<String, Object> note_his = new HashMap<>();

                note.put(KEY_NAME, name);
                note.put(KEY_AGE, age);
                note.put(KEY_HEIGHT, height);
                note.put(KEY_WEIGHT, weight);
                note.put(KEY_TEAM, team);
                note.put(KEY_POSITION, position);

                note_weight.put(KEY_WEIGHT, weight);

                note_his.put(KEY_WEIGHT, weight);
                note_his.put(KEY_WEIGHT_DATE, date);

              String id3 = notref.getId();



              db.collection(user.getEmail()).document("Profile").collection("Weight History").document(id3).set(note_his);






                db.collection(user.getEmail()).document("Profile").set(note)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });




            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile newfrag2 = new Profile();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, newfrag2, null).commit();
            }
        });

        notref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString(KEY_NAME);
                    String age = documentSnapshot.getString(KEY_AGE);
                    String height = documentSnapshot.getString(KEY_HEIGHT);
                    String weight = documentSnapshot.getString(KEY_WEIGHT);
                    String team = documentSnapshot.getString(KEY_TEAM);
                    String pos = documentSnapshot.getString(KEY_POSITION);

                    mName.setText(name);
                    mAge.setText(age);
                    mHeight.setText(height);
                    mWeight.setText(weight);
                    mTeam.setText(team);
                    mPosition.setText(pos);

                } else {
                    Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());

            }
        });

        tWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               eWeight.setVisibility(View.VISIBLE);
               bWeight.setVisibility(View.VISIBLE);
               mSave.setVisibility(View.GONE);

               tWeight.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       mSave.setVisibility(View.VISIBLE);
                       eWeight.setVisibility(View.GONE);
                       bWeight.setVisibility(View.GONE);
                   }
               });


               noteref2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                         if(documentSnapshot.exists()){
                             String targetweight = documentSnapshot.getString(KEY_TARGET_WEIGHT);
                             eWeight.setText(targetweight);
                         }
                   }
               });

               bWeight.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Map<String, Object> note_weight = new HashMap<>();
                       String target_weight = eWeight.getText().toString();
                       note_weight.put(KEY_TARGET_WEIGHT, target_weight);

                       db.collection(user.getEmail()).document("Profile").collection("Weight").document("Target Weight").set(note_weight)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
                                       eWeight.setVisibility(View.GONE);
                                       bWeight.setVisibility(View.GONE);
                                       mSave.setVisibility(View.VISIBLE);
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                               Log.d(TAG, e.toString());
                           }
                       });


                   }
               });

            }
        });




        return RootView;

    }

}