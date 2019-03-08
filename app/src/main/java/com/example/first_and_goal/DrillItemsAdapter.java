package com.example.first_and_goal;

import android.content.Context;
import android.media.ImageReader;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

//code adapted from coding in flow tutorial

public class DrillItemsAdapter extends RecyclerView.Adapter<DrillItemsAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Drillitems> mDrills;

    public DrillItemsAdapter(Context context, List<Drillitems> drills){
        mContext = context;
        mDrills = drills;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.drills_items, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Drillitems drillsCurrent = mDrills.get(position);
        holder.textViewName.setText(drillsCurrent.getName());
        Picasso.with(mContext).load(drillsCurrent.getImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerInside().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mDrills.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView){
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_uploaded_view);
        }
    }
}