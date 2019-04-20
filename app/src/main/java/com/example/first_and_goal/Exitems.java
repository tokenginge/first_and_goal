package com.example.first_and_goal;

public class Exitems {
    private String mName;
    private String mDes;
    private String mImageUrl;

    public Exitems(){
    }

    public Exitems(String name, String des, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No name";
        }
        if (des.trim().equals("")) {
            des = "No name";
        }

        mName = name;
        mDes = des;
        mImageUrl = imageUrl;
    }

    public String getName(){
        return mName;
    }
    public void setName(String name){
        mName = name;
    }

    public String getDes(){
        return mDes;
    }
    public void setDes(String des){
        mDes = des;
    }


    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}