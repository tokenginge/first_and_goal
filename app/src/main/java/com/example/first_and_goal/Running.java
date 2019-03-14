package com.example.first_and_goal;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.Switch;
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

public class Running extends AppCompatActivity implements SensorEventListener, StepListener{

    private TextView mText, Steps, miles, prevsteps, prevmiles, prevtime;
    private  StepDetector mDetector;
    private SensorManager mManager;
    private Sensor mSensor;
    private static final String TEXT_NUM_STEPS = "Number of steps: ";
    private static final String TEXT_MILES = "Miles: ";
    private static final String TAG = "Running:";
    private static final String KEY_STEPS = "Steps";
    private static final String KEY_MILES = "Miles";
    private static final String KEY_TIME = "Time";
    private int numSteps;
    private float numMiles;
    private Button btn_start, btn_save,btn_home;
    private Chronometer mChron;
    private long pauseOffset;
    private boolean running;
    private PowerManager.WakeLock wakeLock;
    private ProgressBar progressBar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference notref = db.collection(user.getEmail()).document("Steps");

    @Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_running);


    mManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mDetector = new StepDetector();
    mDetector.registerListener(this);

    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);


    progressBar = findViewById(R.id.progressBar);
    Steps = findViewById(R.id.steps);
    miles = findViewById(R.id.miles);
    btn_start = findViewById(R.id.btn_start);
    btn_save = findViewById(R.id.btn_stop);
    prevsteps = findViewById(R.id.prevsteps);
    prevmiles = findViewById(R.id.prevmiles);
    prevtime = findViewById(R.id.prevtime);
    mChron = findViewById(R.id.mTimer);
    btn_home = findViewById(R.id.btn_back);
    mChron.setFormat("Time: %ss");
    mChron.setBase(SystemClock.elapsedRealtime());


        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        animation.setDuration(5000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                Toast.makeText(Running.this, "Good", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();


    btn_home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivity (new Intent(Running.this, home.class));
        }
    });


        notref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String newSteps = documentSnapshot.getString(KEY_STEPS);
                String newMiles = documentSnapshot.getString(KEY_MILES);
                String newTime = documentSnapshot.getString(KEY_TIME);

                if (newTime != null) {
                    prevsteps.setText("Previous " + newSteps);
                    prevmiles.setText("Previous " + newMiles);
                    prevtime.setText("Previous " + newTime);

                }else {

                    Toast.makeText(Running.this, "No previous running", Toast.LENGTH_SHORT).show();
                }
            }

        });


    btn_start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btn_start.setVisibility(View.GONE);
            btn_save.setVisibility(View.VISIBLE);
            numSteps = 0;
            mManager.registerListener(Running.this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);

            if (!running){
                mChron.setBase(SystemClock.elapsedRealtime());
                mChron.start();
                running = true;
            }




        }
    });

    btn_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btn_save.setVisibility(View.GONE);
            btn_start.setVisibility(View.VISIBLE);
            mManager.unregisterListener(Running.this);
            String steps = Steps.getText().toString();
            String Mmiles = miles.getText().toString();
            String time = mChron.getText().toString();

            Map<String, Object> note = new HashMap<>();
            note.put(KEY_STEPS, steps);
            note.put(KEY_MILES, Mmiles);
            note.put(KEY_TIME, time);

            mChron.setBase(SystemClock.elapsedRealtime());
            mChron.stop();
            running = false;

            db.collection(user.getEmail()).document("Steps").set(note);

            notref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String newSteps = documentSnapshot.getString(KEY_STEPS);
                    String newMiles = documentSnapshot.getString(KEY_MILES);
                    String newTime = documentSnapshot.getString(KEY_TIME);

                    prevsteps.setText("Previous " + newSteps);
                    prevmiles.setText("Previous " + newMiles);
                    prevtime.setText("Previous " + newTime);
                }
            });

        }
    });





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);


        }
    }

    @Override
    public void step(long timeNs){
        numSteps++;
        Steps.setText(TEXT_NUM_STEPS + numSteps);
        numMiles = numSteps/880;
        miles.setText(TEXT_MILES + numMiles);


    }
}