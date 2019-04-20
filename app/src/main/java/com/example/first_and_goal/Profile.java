package com.example.first_and_goal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;


public class Profile extends Fragment {

    private static final String TAG = "Profile";
    private static final String KEY_NAME = "Name";
    private static final String KEY_AGE ="Age";
    private static final String KEY_HEIGHT = "Height (ft)";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_TEAM = "Team";
    private static final String KEY_POSITION = "Position";
    private static final String KEY_TARGET_WEIGHT = "Target Weight";
    private static final String KEY_HEIGHT_2 = "Height (in)";
    private Button edit;
    private TextView profName;
    private TextView profAge;
    private TextView profHeight;
    private TextView profWeight;
    private TextView profTeam;
    private TextView tWeight;
    private TextView profPos;
    private TextView profHeight2;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference notref = db.collection(user.getEmail()).document("Profile");
    private DocumentReference weightref = db.collection(user.getEmail()).document("Profile").collection("Weight").document("Target Weight");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile, container, false);

        edit = RootView.findViewById(R.id.btn_Edit);
        profAge = RootView.findViewById(R.id.prof_age);
        profName = RootView.findViewById(R.id.prof_name);
        profHeight = RootView.findViewById(R.id.prof_height);
        profWeight = RootView.findViewById(R.id.prof_weight);
        profTeam = RootView.findViewById(R.id.prof_team);
        profPos = RootView.findViewById(R.id.prof_pos);
        tWeight = RootView.findViewById(R.id.weight_dif);
        profHeight2 = RootView.findViewById(R.id.prof_height_2);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile2 newfrag2 = new profile2();
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
                    final String weight = documentSnapshot.getString(KEY_WEIGHT);
                    String team = documentSnapshot.getString(KEY_TEAM);
                    String pos = documentSnapshot.getString(KEY_POSITION);
                    String height2 = documentSnapshot.getString(KEY_HEIGHT_2);

                    profName.setText(name);
                    profAge.setText(age);
                    profHeight.setText(height);
                    profTeam.setText(team);
                    profPos.setText(pos);
                    profWeight.setText(weight);
                    profHeight2.setText(height2);
                    weightref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String targetweight = documentSnapshot.getString(KEY_TARGET_WEIGHT);
                            tWeight.setText(targetweight);



                            if (targetweight == "" || weight == "") {

                                Toast.makeText(getActivity(), "Nothing to compare", Toast.LENGTH_SHORT).show();
                            }
                                    else {
                                assert targetweight != null;
                                if (targetweight.equals(weight) && targetweight != ""){
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setMessage("Well done! you have met your target weight");
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            dialog.setCancelable(true);
                            dialog.create().show();


                        }
                        else{
                            Toast.makeText(getActivity(), "Not yet at target weight!", Toast.LENGTH_SHORT).show();



                        }
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity(), "Tell DAN!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                    );




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





        return RootView;
    }


}






