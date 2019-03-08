package com.example.first_and_goal;

public class Upload {
    private String mname;
    private String mImageURL;

    public Upload (){

    }

    public Upload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mname = name;
        mImageURL = imageUrl;

    }

    public String getName(){
        return mname;
    }

    public void setName(String name){
        mname = name;
    }

    public String getImageUrl(){
        return mImageURL;
    }

    public void setImageUrl(String imageUrl){
        mImageURL = imageUrl;
    }


}
