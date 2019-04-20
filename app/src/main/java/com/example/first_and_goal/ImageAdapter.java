package com.example.first_and_goal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder2> {
    private Context mcontext;
    private List<workout_upload> mUploads;
    private OnMenuItemClickListener mListener;
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference dbref = db2.getReference(user.getUid()).child("Workout").child("1");



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





    public class ImageViewHolder2 extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener{
        public CheckedTextView textViewWork;
        public ImageView option;





        public ImageViewHolder2(View itemView) {
            super(itemView);

            textViewWork = itemView.findViewById(R.id.text_view_work);
            option = itemView.findViewById(R.id.options);



            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(mcontext, view);
                    popupMenu.setOnMenuItemClickListener(ImageViewHolder2.this);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.show();

                }
            });
        }







        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
          if (mListener != null){
              int position = getAdapterPosition();
              if (position!=RecyclerView.NO_POSITION){
                  switch (menuItem.getItemId()) {
                      case R.id.delete:
                          mListener.onDeleteClick(position);

                          return true;

                  }
                  }
              }
              return false;
          }


    }

    public interface OnMenuItemClickListener {
        void onDeleteClick (int position);
    }

    public void setOnMenuItemClickListener (OnMenuItemClickListener listener){
        mListener = listener;
    }
}










