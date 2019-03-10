package com.example.first_and_goal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class home_frag extends Fragment {



    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Profile").child("Weight");
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference weightref = db2.getReference(user.getUid()).child("Profile").child("Weight History");
    private SimpleDateFormat sdf = new SimpleDateFormat ("hh:mm");
    private GraphView graphView;
    private TextView weightHistory, closeHistory;
    private LineGraphSeries series;
    private List<weight_upload> mUploads;
    private RecyclerView recyclerView;
    private WeightAdapter imageAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_home_frag, container, false);

        graphView = RootView.findViewById(R.id.weight_graph);
        weightHistory = RootView.findViewById(R.id.weightHist);
        closeHistory = RootView.findViewById(R.id.weightClose);
        recyclerView = RootView.findViewById(R.id.weight_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        series = new LineGraphSeries();

        graphView.addSeries(series);
        mUploads = new ArrayList<>();
        final ArrayAdapter<weight_upload> listAdapter = new ArrayAdapter<weight_upload>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mUploads);



        graphView.getGridLabelRenderer().setVerticalAxisTitle("Weight");
       graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
       graphView.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
       series.setColor(Color.WHITE);
       series.setThickness(2);
       graphView.setBackgroundColor(getResources().getColor(R.color.colorBlack));



        graphView.getGridLabelRenderer().setGridColor(Color.WHITE);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getViewport().getYAxisBoundsStatus();
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);



        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        //{
          //  @Override
            //public String formatLabel(double value, boolean isValueX) {
//
  //              if (isValueX){
    //                return sdf.format(new Date((long) value));
      //          }
        //            else {
//
  //              return super.formatLabel(value, isValueX);}
    //        }
      //  });

        series.setAnimated(true);

weightHistory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(final View view) {
        recyclerView.setVisibility(View.VISIBLE);
        weightHistory.setVisibility(View.GONE);
        closeHistory.setVisibility(View.VISIBLE);
        weightref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    weight_upload upload = postSnapshot.getValue(weight_upload.class);
                    mUploads.add(upload);
                }

                imageAdapter = new WeightAdapter(getActivity(), mUploads);
                recyclerView.setAdapter(imageAdapter);


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
});

closeHistory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        recyclerView.setVisibility(View.GONE);
        closeHistory.setVisibility(View.GONE);
        weightHistory.setVisibility(View.VISIBLE);
    }
});



        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot mSnapshot : dataSnapshot.getChildren()) {
                    PointValue pointValue = mSnapshot.getValue(PointValue.class);

                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }

                series.resetData(dp);
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "point"+dataPoint, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            }



        }



