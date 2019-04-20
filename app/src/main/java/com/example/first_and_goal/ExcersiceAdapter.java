package com.example.first_and_goal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExcersiceAdapter extends RecyclerView.Adapter<ExcersiceAdapter.ImageViewHolder2> {
    private Context mcontext;
    private List<Exitems> mUploads;
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Workout").child("1");
    private DatabaseReference dbref1 = db2.getReference(user.getUid()).child("Workout").child("2");
    private DatabaseReference dbref2 = db2.getReference(user.getUid()).child("Workout").child("3");
    private DatabaseReference dbref3 = db2.getReference(user.getUid()).child("Workout").child("4");
    private DatabaseReference dbref4 = db2.getReference(user.getUid()).child("Workout").child("5");
    private DocumentReference workref = db.collection(user.getEmail()).document("Workouts");
    private static final String KEY_WORK_1 = "Workout 1";
    private static final String KEY_WORK_2 = "Workout 2";
    private static final String KEY_WORK_3 = "Workout 3";
    private static final String KEY_WORK_4 = "Workout 4";
    private static final String KEY_WORK_5 = "Workout 5";

    public ExcersiceAdapter(Context context, List<Exitems> uploads){
        mcontext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder2 onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mcontext).inflate(R.layout.exercise_items, parent, false);
        return new ImageViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder2 holder, final int position){
        final Exitems uploadNow = mUploads.get(position);
        holder.textViewEx.setText(uploadNow.getName());
        holder.textViewDes.setText(uploadNow.getDes());
        Picasso.with(mcontext).load(uploadNow.getImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerInside().into(holder.imageView);

        workref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                final String work1 = documentSnapshot.getString(KEY_WORK_1);
                final String work2 = documentSnapshot.getString(KEY_WORK_2);
                final String work3 = documentSnapshot.getString(KEY_WORK_3);
                final String work4 = documentSnapshot.getString(KEY_WORK_4);
                final String work5 = documentSnapshot.getString(KEY_WORK_5);


                holder.textViewEx.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        Context context = mcontext;
                        final LinearLayout layout = new LinearLayout(context);
                        final EditText reps = new EditText(context);
                        final EditText sets = new EditText(context);
                        final  RadioButton work_1 = new RadioButton(context);
                        final RadioButton work_2 = new RadioButton(context);
                        final  RadioButton work_3 = new RadioButton(context);
                        final RadioButton work_4 = new RadioButton(context);
                        final RadioButton work_5 = new RadioButton(context);

                        reps.setHint("Reps");
                        reps.setInputType(InputType.TYPE_CLASS_NUMBER);
                        sets.setInputType(InputType.TYPE_CLASS_NUMBER);
                        sets.setHint("Sets");
                        work_2.setText(work2);
                        work_1.setText(work1);
                        work_3.setText(work3);
                        work_4.setText(work4);
                        work_5.setText(work5);

                        layout.addView(sets);
                        layout.addView(reps);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.addView(work_1);
                        layout.addView(work_2);
                        layout.addView(work_3);
                        layout.addView(work_4);
                        layout.addView(work_5);
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Add exercise");
                        dialog.setView(layout);
                        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {



                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            workout_upload upload = new workout_upload(uploadNow.getName().trim(), sets.getText().toString().trim(), reps.getText().toString().trim());


                                            if(work_1.isChecked()){
                                            dbref.child(uploadNow.getName()).setValue(upload);}
                                            if(work_2.isChecked()){
                                                dbref1.child(uploadNow.getName()).setValue(upload);
                                            }
                                            if(work_3.isChecked()){
                                                dbref2.child(uploadNow.getName()).setValue(upload);}
                                            if(work_4.isChecked()){
                                                dbref3.child(uploadNow.getName()).setValue(upload);
                                            }
                                            if(work_5.isChecked()){
                                                dbref4.child(uploadNow.getName()).setValue(upload);}



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





                        return true;
                    }
                });
            }
        });


        }





        public int getItemCount(){
            return mUploads.size();
        }


    public class ImageViewHolder2 extends RecyclerView.ViewHolder{
        public TextView textViewEx;
        public TextView textViewDes;
        public ImageView imageView;








    public ImageViewHolder2(View itemView){
                    super(itemView);

                    textViewEx = itemView.findViewById(R.id.text_view_ex);
                    textViewDes = itemView.findViewById(R.id.text_view_des);
                    imageView = itemView.findViewById(R.id.ex_image);




                }
            }
        }
















