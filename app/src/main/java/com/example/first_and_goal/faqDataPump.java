package com.example.first_and_goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class faqDataPump {

    public static HashMap<String, List<String>> getData(){
        HashMap<String, List<String>> faqDetail = new HashMap<String, List<String>>();

        List<String> Question1 = new ArrayList<String>();
        Question1.add("No absolutely not! First & Goal can be used by American Football players at any level, even Rookies!");

        List<String> Question2 = new ArrayList<String>();
        Question2.add("This will be another answer to the question");

        List<String> Question3 = new ArrayList<String>();
        Question3.add("This will be another answer to the question");

        List<String> Question4 = new ArrayList<String>();
        Question4.add("Surprise!!");


        faqDetail.put("Do i need to be a pro in order to use this app?", Question1);
        faqDetail.put("Question 2", Question2);
        faqDetail.put("Question 3", Question3);
        faqDetail.put("Surprise?", Question4);

        return faqDetail;


    }
}
