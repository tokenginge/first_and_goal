package com.example.first_and_goal;

import com.google.firebase.database.Exclude;

public class workout_upload {
    private String mWork;
    private String mSets;
    private String mReps;
    private String mKey;

    public workout_upload(){}

    public workout_upload(String work, String sets, String reps){
        if(work.trim().equals("")){
            work = "No workout name";
        }
        if (sets.trim().equals("")){
            sets = "0";
        }
        if (reps.trim().equals("")){
            reps = "0";
        }
        mWork = work;
        mSets = sets;
        mReps = reps;
    }
    public String getWork(){
        return mWork;
    }

    public void setWork(String work){
        mWork = work;
    }
    public String getSets(){
        return mSets;
    }
    public void setSets(String sets){
        mSets = sets;
    }
    public String getReps(){
        return mReps;
    }
    public void setReps(String reps){
        mReps = reps;
    }

    @Exclude
    public String getKey(){
        return mKey;
    }

    @Exclude
    public void setKey(String key){
        mKey = key;
    }
}
