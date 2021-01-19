package com.example.teamunited.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamunited.Modal.NewsItem;
import com.example.teamunited.Modal.PDFModel;
import com.example.teamunited.R;
import com.example.teamunited.callback.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.Holder> implements Filterable {

    private List<PDFModel> list;
    private Context context;
    ItemClickListener itemClickListener;
    List<PDFModel> mDataFiltered ;

    boolean isDark = false;

    public PDFAdapter(List<PDFModel> list, Context context, ItemClickListener itemClickListener, boolean isDark) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;

        this.mDataFiltered = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pdf_item,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

       holder.imageView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
       holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));

        holder.pdfName.setText(mDataFiltered.get(position).getPdfName());
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = list ;

                }
                else {
                    List<PDFModel> lstFiltered = new ArrayList<>();
                    for (PDFModel row : list) {

                        if (row.getPdfName().toLowerCase().contains(Key.toLowerCase())){
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
                mDataFiltered = (List<PDFModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pdfName;
        private ImageView imageView;
        private RelativeLayout relativeLayout;

        public Holder(View itemView) {
            super(itemView);

            pdfName = itemView.findViewById(R.id.TV);
            imageView = itemView.findViewById(R.id.IV);
            relativeLayout=itemView.findViewById(R.id.rl);
            relativeLayout.setOnClickListener(this);
            if (isDark) {
                setDarkTheme();
            }
        }
        private void setDarkTheme() {
            relativeLayout.setBackgroundResource(R.drawable.card_bg_dark);
        }
        @Override
        public void onClick(View v)
        {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}
