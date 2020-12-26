package com.example.teamunited.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamunited.Modal.NewsItem;
import com.example.teamunited.R;
import com.example.teamunited.callback.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> implements Filterable {
    Context mContext;
    List<NewsItem> mData ;
    List<NewsItem> mDataFiltered ;
    boolean isDark = false;
    ItemClickListener itemClickListener;

    public VideoAdapter(Context mContext, List<NewsItem> mData, boolean isDark,ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered = mData;
        this.itemClickListener = itemClickListener;
    }

    public VideoAdapter(Context mContext, List<NewsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<NewsItem> lstFiltered = new ArrayList<>();
                    for (NewsItem row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (List<NewsItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_video,parent,false);
        return new VideoAdapter.VideoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        // first lets create an animation for user photo
        holder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));



        holder.tv_title.setText(mDataFiltered.get(position).getTitle());
        holder.tv_content.setText(mDataFiltered.get(position).getContent());
        holder.tv_date.setText(mDataFiltered.get(position).getDate());
        holder.img_user.setImageResource(mDataFiltered.get(position).getUserPhoto());

    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView tv_title,tv_content,tv_date;
        ImageView img_user;
        LinearLayout container;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.video_tools_title);
            tv_content = itemView.findViewById(R.id.video_tools_description);
            tv_date = itemView.findViewById(R.id.date);
            img_user = itemView.findViewById(R.id.video_tools_image);
          container.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }

}
