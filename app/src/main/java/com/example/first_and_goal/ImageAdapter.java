package com.example.first_and_goal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder2> {
    private Context mcontext;
    private List<workout_upload> mUploads;

    public ImageAdapter(Context context, List<workout_upload> uploads){
        mcontext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder2 onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mcontext).inflate(R.layout.work_items, parent, false);
        return new ImageViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder2 holder, int position){
        final workout_upload uploadNow = mUploads.get(position);
        holder.textViewWork.setText(uploadNow.getWork() + " " + " - " + " "+ uploadNow.getSets() + " " + " x " + " "+ uploadNow.getReps());
        holder.textViewWork.setChecked(false);
        holder.textViewWork.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mcontext, "Good test!!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.textViewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.textViewWork.isChecked()) {
                holder.textViewWork.setChecked(false);
                holder.textViewWork.setText(uploadNow.getWork() + " " + " - " + " "+ uploadNow.getSets() + " " + " x " + " "+ uploadNow.getReps());

                }else {
                    holder.textViewWork.setChecked(true);
                    holder.textViewWork.setText("Done");
                }

            }
        });




    }

    @Override
    public int getItemCount(){
        return mUploads.size();
    }

    public class ImageViewHolder2 extends RecyclerView.ViewHolder{
        public CheckedTextView textViewWork;
        public TextView textViewSets;
        public TextView textViewReps;




        public ImageViewHolder2(View itemView){
            super(itemView);

            textViewWork = itemView.findViewById(R.id.text_view_work);




                        }
                    }
                }










