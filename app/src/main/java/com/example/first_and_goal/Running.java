package com.example.first_and_goal;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.media.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class Running extends AppCompatActivity implements SensorEventListener, StepListener {

    private TextView Steps, miles, tMiles;
    private StepDetector mDetector;
    private SensorManager mManager;
    private Sensor mSensor;
    private static final String TEXT_NUM_STEPS = "Number of steps: ";
    private static final String TEXT_MILES = "Miles: ";
    private static final String TAG = "Running:";
    private static final String KEY_STEPS = "Steps";
    private static final String KEY_MILES = "Miles";
    private static final String KEY_TIME = "Time";
    private static final String KEY_DATE = "Date";
    private int numSteps;
    private Button btn_start, btn_save, btn_home, btn_prev, close;
    private Chronometer mChron;
    private boolean running;
    private Timer mTimer;
    private PowerManager.WakeLock wakeLock;
    private ProgressBar progressBar;
    private List<running_upload> mUploads;
    private RecyclerView recyclerView;
    private RunAdapter imageAdapter;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference notref = db.collection(user.getEmail()).document("Steps");
    private DatabaseReference stepHis = db2.getReference(user.getUid()).child("Steps").child("Step History");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);


        mManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mDetector = new StepDetector();
        mDetector.registerListener(this);
        mUploads = new ArrayList<>();


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);


        progressBar = findViewById(R.id.progress_big);
        Steps = findViewById(R.id.steps);
        miles = findViewById(R.id.miles);
        btn_start = findViewById(R.id.btn_start);
        btn_save = findViewById(R.id.btn_stop);
        btn_prev = findViewById(R.id.btn_prev);
        tMiles = findViewById(R.id.tMiles);
        close = findViewById(R.id.btn_close);
        recyclerView = findViewById(R.id.run_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mChron = findViewById(R.id.mTimer);
        btn_home = findViewById(R.id.btn_back);
        mChron.setFormat("Time: %ss");
        mChron.setBase(SystemClock.elapsedRealtime());


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Running.this, home.class));
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                btn_prev.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);

                stepHis.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mUploads.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            running_upload upload = postSnapshot.getValue(running_upload.class);
                            mUploads.add(upload);


                            imageAdapter = new RunAdapter(Running.this, mUploads);
                            recyclerView.setAdapter(imageAdapter);
                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close.setVisibility(View.GONE);
                btn_prev.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                recyclerView.removeAllViews();
            }
        });


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(Running.this);
                final EditText maxSteps = new EditText(Running.this);
                btn_home.setVisibility(View.INVISIBLE);
                maxSteps.setHint("Enter Miles");
                maxSteps.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialog.setTitle("Target Miles");
                dialog.setView(maxSteps);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        btn_start.setVisibility(View.GONE);
                        btn_save.setVisibility(View.VISIBLE);
                        numSteps = 0;
                        mManager.registerListener(Running.this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
                        tMiles.setText("Target: " + maxSteps.getText().toString());
                        btn_save.setVisibility(View.VISIBLE);




                        if (!running) {
                            mChron.setBase(SystemClock.elapsedRealtime());
                            mChron.start();
                            running = true;

                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.create().cancel();
                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();


            }


        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_save.setVisibility(View.GONE);
                btn_start.setVisibility(View.VISIBLE);
                btn_home.setVisibility(View.VISIBLE);
                mManager.unregisterListener(Running.this);
                String steps = Steps.getText().toString();
                String Mmiles = miles.getText().toString();
                String time = mChron.getText().toString();
                String date = new Date().toString().trim();

                running_upload upload = new running_upload(time, date, Mmiles, steps);
                stepHis.child(date).setValue(upload);


                mChron.setBase(SystemClock.elapsedRealtime());
                mChron.stop();
                running = false;



            }
        });


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);


        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        Steps.setText(TEXT_NUM_STEPS + numSteps);


        BigDecimal bg1, bg2, numMiles;
        bg1 = new BigDecimal("880");
        bg2 = new BigDecimal(numSteps);

        numMiles = bg2.divide(bg1, 2, RoundingMode.CEILING);







        progressBar.setProgress(numSteps);

        progressBar.setMax(30);

        if(numSteps == 10) {

            final AlertDialog.Builder dialog = new AlertDialog.Builder(Running.this);
            dialog.setTitle("Well done");
            dialog.setMessage("You have reached 1000 steps!");
            dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.create().cancel();
                }
            });
            dialog.setCancelable(true);
            dialog.create().show();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(Running.this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, numSteps, pendingIntent);
            }

        }



            if(numSteps==20){



                final AlertDialog.Builder dialog2 = new AlertDialog.Builder(Running.this);
                dialog2.setTitle("Well done");
                dialog2.setMessage("You have reached 200 steps!");
                dialog2.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog2.create().cancel();
                    }
                });
                dialog2.setCancelable(true);
                dialog2.create().show();

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(Running.this, AlertReceiver3.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, numSteps, pendingIntent);
                }

            }











        miles.setText(TEXT_MILES + numMiles);


    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Running.this, home.class));

    }



}