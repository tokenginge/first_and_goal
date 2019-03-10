package com.example.first_and_goal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class weight_upload {
    private String mdate;
    private String mWeight;

    public weight_upload(){}

    public weight_upload(String weight, String date){

        if (weight.equals("")){
            weight = "0";
        }

        mWeight = weight;
        mdate = date;

    }
    public String getWeight(){
        return mWeight;
    }

    public void setWeight(String weight){
        mWeight = weight;
    }
    public String getDate(){
        return mdate;
    }
    public void setDate (String date){
        mdate = date;
    }

}
