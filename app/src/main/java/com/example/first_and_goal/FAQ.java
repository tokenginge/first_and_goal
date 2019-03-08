package com.example.first_and_goal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FAQ extends Fragment {

    ExpandableListView faqList;
    ExpandableListAdapter faqAdapter;
    List<String> faqTitle;
    HashMap<String, List<String>> faqDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_faq, container, false);

        faqList = RootView.findViewById(R.id.faq_list);
        faqDetail = faqDataPump.getData();
        faqTitle = new ArrayList<String>(faqDetail.keySet());
        faqAdapter = new faqAdapter(getActivity(), faqTitle, faqDetail);
        faqList.setAdapter(faqAdapter);
        faqList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {


            }
        });
        faqList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });

        faqList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getActivity(), faqTitle.get(i) + "->" + faqDetail.get(faqTitle.get(i)).get(
                        i1), Toast.LENGTH_SHORT).show();
                return false;
            }
        });






        return RootView;
    }
}
