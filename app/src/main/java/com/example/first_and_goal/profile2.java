package com.example.first_and_goal;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class profile2 extends Fragment implements DatePickerDialog.OnDateSetListener{
    private static final String TAG = "profile2";
    private static final String KEY_NAME = "Name";
    private static final String KEY_AGE ="Age";
    private static final String KEY_HEIGHT = "Height (ft)";
    private static final String KEY_HEIGHT_2 = "Height (in)";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_TEAM = "Team";
    private static final String KEY_POSITION = "Position";
    private static final String KEY_TARGET_WEIGHT = "Target Weight";
    private static final String KEY_WEIGHT_DATE = "Date";
    private static final String KEY_AGE_DAY = "dd";
    private static final String KEY_AGE_MONTH = "mm";
    private static final String KEY_AGE_YEAR = "yyyy";



    private EditText mName;
    private EditText mHeight, mWeight, mTeam, mPosition, eWeight, mHeight_2, mAge_Calc, age_day, age_month, age_year;
    private ImageView datePicker;
    private Button mSave, mBack,tWeight, bWeight;
    private FragmentActivity mContext;

    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference notref = db.collection(user.getEmail()).document("Profile");
    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Profile").child("Weight");
   private DatabaseReference weightHis = db2.getReference(user.getUid()).child("Profile").child("Weight History");
    private DocumentReference noteref2= db.collection(user.getEmail()).document("Profile").collection("Weight").document("Target Weight");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile2, container, false);


        mName = RootView.findViewById(R.id.edit_name);
        age_day = RootView.findViewById(R.id.edit_day);
        age_month = RootView.findViewById(R.id.edit_month);
        age_year = RootView.findViewById(R.id.edit_year);
        mSave = RootView.findViewById(R.id.save);
        mBack = RootView.findViewById(R.id.btn_back);
        mHeight = RootView.findViewById(R.id.edit_height);
        mWeight = RootView.findViewById(R.id.edit_weight);
        mTeam = RootView.findViewById(R.id.edit_team);
        mPosition = RootView.findViewById(R.id.edit_position);
        tWeight= RootView.findViewById(R.id.t_Weight);
        eWeight = RootView.findViewById(R.id.enter_target_weight);
        bWeight = RootView.findViewById(R.id.save_target_weight);
        mHeight_2 = RootView.findViewById(R.id.edit_height_2);
        mAge_Calc = RootView.findViewById(R.id.edit_age_calc);




        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int day = Integer.parseInt(age_day.getText().toString());
                int month = Integer.parseInt(age_month.getText().toString());
                int year = Integer.parseInt(age_year.getText().toString());

                String age = getAge(year, month, day);

                String name = mName.getText().toString();

                String height = mHeight.getText().toString();
                String weight = mWeight.getText().toString();
                String team = mTeam.getText().toString();
                String position = mPosition.getText().toString();
                String height2 = mHeight_2.getText().toString();
                String mday = age_day.getText().toString();
                String mMonth = age_month.getText().toString();
                String mYear = age_year.getText().toString();
                String date = new Date().toString().trim();


                if (weight.equals("")) {
                    Toast.makeText(getActivity(), "No weight entered", Toast.LENGTH_SHORT).show();
                } else {
                    long x = new Date().getTime();
                    float y = Float.parseFloat(weight);

                    PointValue pointValue = new PointValue(x, y);

                    String id = dbref.push().getKey();


                    dbref.child(id).setValue(pointValue);
                }

                weight_upload upload = new weight_upload(weight, date);
                weightHis.child(date).setValue(upload);


                Map<String, Object> note = new HashMap<>();
                Map<String, Object> note_weight = new HashMap<>();
                Map<String, Object> note_his = new HashMap<>();

                note.put(KEY_NAME, name);
                note.put(KEY_AGE, age);
                note.put(KEY_HEIGHT, height);
                note.put(KEY_WEIGHT, weight);
                note.put(KEY_TEAM, team);
                note.put(KEY_POSITION, position);
                note.put(KEY_HEIGHT_2, height2);
                note.put(KEY_AGE_DAY, mday);
                note.put(KEY_AGE_MONTH, mMonth);
                note.put(KEY_AGE_YEAR, mYear);

                note_weight.put(KEY_WEIGHT, weight);

                note_his.put(KEY_WEIGHT, weight);
                note_his.put(KEY_WEIGHT_DATE, date);

                String id3 = String.valueOf(SystemClock.currentThreadTimeMillis());


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
                    String day = documentSnapshot.getString(KEY_AGE_DAY);
                    String month = documentSnapshot.getString(KEY_AGE_MONTH);
                    String year = documentSnapshot.getString(KEY_AGE_YEAR);
                    String height = documentSnapshot.getString(KEY_HEIGHT);
                    String weight = documentSnapshot.getString(KEY_WEIGHT);
                    String team = documentSnapshot.getString(KEY_TEAM);
                    String pos = documentSnapshot.getString(KEY_POSITION);
                    String heigh2 = documentSnapshot.getString(KEY_HEIGHT_2);

                    mName.setText(name);

                    mHeight.setText(height);
                    mWeight.setText(weight);
                    mTeam.setText(team);
                    mPosition.setText(pos);
                    mHeight_2.setText(heigh2);
                    age_day.setText(day);
                    age_month.setText(month);
                    age_year.setText(year);


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

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if(today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return  ageS;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}