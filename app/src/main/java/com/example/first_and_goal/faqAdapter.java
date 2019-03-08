package com.example.first_and_goal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class faqAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> faqTitle;
    private HashMap<String, List<String>> faqDetail;

    public faqAdapter(Context context, List<String> faqTitle, HashMap<String, List<String>> faqDetail){

        this.mContext = context;
        this.faqTitle = faqTitle;
        this.faqDetail = faqDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition){
        return this.faqDetail.get(this.faqTitle.get(listPosition)).get(expandedListPosition);
    }
    @Override
    public long getChildId(int listPosition, int expandedListPosition){
        return expandedListPosition;
    }
    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent){
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_item, null);
        }
        TextView faqITem = convertView.findViewById(R.id.faq_item);
        faqITem.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition){
        return this.faqDetail.get(this.faqTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition){
        return this.faqTitle.get(listPosition);
    }

    @Override
    public int getGroupCount(){
        return this.faqTitle.size();
    }

    @Override
    public long getGroupId(int listPosition){
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String listTitle = (String)getGroup(listPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_group, null);
        }
        TextView faqTitle = convertView.findViewById(R.id.faq_title);
        faqTitle.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds(){
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition){
        return true;
    }

}
