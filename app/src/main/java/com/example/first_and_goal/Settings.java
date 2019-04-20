package com.example.first_and_goal;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private Button signout, timepicker, cancelAlarm;
    private TextView email, mTimer;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FragmentActivity myContext;
    private String FILE_NAME = "Alarm";
    private static final String KEY_ALARM = "Alarm Time:";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference notref = db.collection(user.getEmail()).document("Alarm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);


        notref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String timeText = "Alarm set for: ";


                    String alarm = documentSnapshot.getString(KEY_ALARM);




                    timeText += alarm;

                    mTimer.setText(timeText);





                }
            }
        });

        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            int read = -1;
            StringBuffer buffer = new StringBuffer();

            Log.d("Alarm", buffer.toString());
            String alarm = buffer.substring(buffer.indexOf(""));

            mTimer.setText(alarm);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        auth = FirebaseAuth.getInstance();
        signout = findViewById(R.id.sign_out);
        mTimer = findViewById(R.id.textView);
        timepicker = findViewById(R.id.button_timepicker);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });


        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.useremail);

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment mtimePicker = new TimePickerFragment();
                // FragmentManager fragmentManager = myContext.getFragmentManager();
                mtimePicker.show(getFragmentManager(), "time picker");
            }
        });

        cancelAlarm = findViewById(R.id.button_cancel);
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(Settings.this, login.class);
                    startActivity(intent);
                }
            }
        };


        signout = findViewById(R.id.sign_out);


        progressBar = findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

    }


    private void setDataToView(@NonNull FirebaseUser user) {
        email.setText("User Email: " + user.getEmail());
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                Intent intent = new Intent(Settings.this, login.class);
                startActivity(intent);
            } else {
                setDataToView(user);
            }
        }
    };

    public void signout() {
        auth.signOut();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(Settings.this, login.class);

                    startActivity(intent);
                    Toast.makeText(Settings.this, "Logged out", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);


        updateTimeText(c);

        startAlarm(c);


        File file = null;
        FileOutputStream outputStream = null;
        String FILE_NAME = "Alarm";


        try {
            file = getFilesDir();
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(Integer.parseInt(String.valueOf(c)));

            Toast.makeText(this, "Saved" + file, Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";

        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        String alarm = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Map<String, Object> note = new HashMap<>();

        note.put(KEY_ALARM, alarm);

        notref.set(note);




            mTimer.
                    setText(timeText);
        }










    private void startAlarm(Calendar c){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Settings.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(Settings.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1, intent, 0);

        alarmManager.cancel(pendingIntent);
        mTimer.setText("Alarm Canceled");

        String alarm = mTimer.getText().toString();
        Map<String, Object> note = new HashMap<>();

        note.put(KEY_ALARM, alarm);

        notref.set(note);



    }


    @Override
    public void onBackPressed(){

        finish();
    }





}




