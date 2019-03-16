package com.example.first_and_goal;

public class Exitems {
    private String mName;

    public Exitems(){
    }

    public Exitems(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No name";
        }

        mName = name;
    }

    public String getName(){
        return mName;
    }
    public void setName(String name){
        mName = name;
    }


}