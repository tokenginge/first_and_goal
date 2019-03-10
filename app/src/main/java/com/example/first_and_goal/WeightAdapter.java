package com.example.first_and_goal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.ImageViewHolder2> {
    private Context mcontext;
    private List<weight_upload> mUploads;

    public WeightAdapter(Context context, List<weight_upload> uploads){
        mcontext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder2 onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mcontext).inflate(R.layout.weight_items, parent, false);
        return new ImageViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder2 holder, int position){
        final weight_upload uploadNow = mUploads.get(position);
        holder.textViewWork.setText(uploadNow.getWeight() + " " + uploadNow.getDate());





    }

    @Override
    public int getItemCount(){
        return mUploads.size();
    }

    public class ImageViewHolder2 extends RecyclerView.ViewHolder{
        public TextView textViewWork;
        public TextView textViewSets;
        public TextView textViewReps;




        public ImageViewHolder2(View itemView){
            super(itemView);

            textViewWork = itemView.findViewById(R.id.text_view_work);




                        }
                    }
                }










