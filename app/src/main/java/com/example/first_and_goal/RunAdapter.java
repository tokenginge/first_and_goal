package com.example.first_and_goal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RunAdapter extends RecyclerView.Adapter<RunAdapter.ImageViewHolder2> {
    private Context mcontext;
    private List<running_upload> mUploads;

    public RunAdapter(Context context, List<running_upload> uploads){
        mcontext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder2 onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mcontext).inflate(R.layout.run_items, parent, false);
        return new ImageViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder2 holder, int position){
        final running_upload uploadNow = mUploads.get(position);
        holder.textViewWork.setText(uploadNow.getSteps() + " " + uploadNow.getMiles() + " " + uploadNow.getTime() + " " + uploadNow.getDate());





    }

    @Override
    public int getItemCount(){
        return mUploads.size();
    }

    public class ImageViewHolder2 extends RecyclerView.ViewHolder{
        public TextView textViewWork;





        public ImageViewHolder2(View itemView){
            super(itemView);

            textViewWork = itemView.findViewById(R.id.text_view_work);




                        }
                    }
                }










