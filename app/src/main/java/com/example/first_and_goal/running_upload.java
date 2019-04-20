package com.example.first_and_goal;

public class running_upload {
    private String mdate;
    private String mTime;
    private String mMiles;
    private String mSteps;


    public running_upload(){}

    public running_upload(String time, String date, String miles, String steps){

        if (time.equals("")){
            time = "0";
        }

        if (miles.equals("")){
            miles = "0";
        }

        if (steps.equals("")){
            steps = "0";
        }

        mTime = time;
        mdate = date;
        mMiles = miles;
        mSteps = steps;

    }
    public String getTime(){
        return mTime;
    }
    public void setTime(String time){
        mTime = time;
    }
    public String getDate(){
        return mdate;
    }
    public void setDate (String date){
        mdate = date;
    }
    public String getMiles(){
        return mMiles;
    }
    public void setMiles(String mile){
        mMiles = mile;
    }
    public String getSteps(){
        return mSteps;
    }
    public void setSteps (String steps){mSteps = steps;}

}
