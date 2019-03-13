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
        Question2.add("If you're a University student then you should find more information from your activities officer. If not you can find out more at BAFA https://britishamericanfootball.org/");

        List<String> Question3 = new ArrayList<String>();
        Question3.add("Performing any sort of exercise is always better with a buddy. Not only do they provide company, they can also give you hints and tips on your form");

        List<String> Question4 = new ArrayList<String>();
        Question4.add("If you are facing any issues with the app then please get in touch with me and I will provide a fix");


        faqDetail.put("Do i need to be a pro in order to use this app?", Question1);
        faqDetail.put("How do i get involved with American Football?", Question2);
        faqDetail.put("What is the best way to use this app?", Question3);
        faqDetail.put("What if i am facing issues with this app?", Question4);

        return faqDetail;


    }
}
