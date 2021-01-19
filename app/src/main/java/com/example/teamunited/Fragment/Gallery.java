package com.example.teamunited.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamunited.Activity.Full_Image;
import com.example.teamunited.Activity.News;
import com.example.teamunited.Activity.Progress;
import com.example.teamunited.Adapter.GalleryAdapter;
import com.example.teamunited.Adapter.GalleryAdapterr;
import com.example.teamunited.MainActivity;
import com.example.teamunited.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

public class Gallery extends Fragment {
    GridView gridView;
    TextView text;

    ArrayList<Integer> mImageIds = new ArrayList < >(Arrays.asList(

            R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,
            R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,
            R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,
            R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,
            R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,
            R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.videoo,R.drawable.achievement


    ));



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_gallery, container, false);

        gridView=view.findViewById(R.id.grid_view);


        gridView.setAdapter(new GalleryAdapterr(mImageIds,getActivity()));


        //gridView.setAdapter(new GalleryAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int item_pos = mImageIds.get(i);
                ShowDialogBox(item_pos);

                /*
                Intent intent=new Intent(getActivity(), Full_Image.class);
                intent.putExtra("id",i);

                startActivity(intent);

*/
            }
        });


        //init();

        return  view;
    }


    public void ShowDialogBox(final int item_pos){
        final Dialog dialog = new Dialog(getActivity());


        dialog.setContentView(R.layout.custom_dialog);

        //Getting custom dialog views
        TextView Image_name = dialog.findViewById(R.id.txt_Image_name);
        ImageView Image = dialog.findViewById(R.id.img);
        Button btn_Full = dialog.findViewById(R.id.btn_full);
        Button btn_Close = dialog.findViewById(R.id.btn_close);

        String title = getResources().getResourceName(item_pos);

        //extracting name
        int index = title.indexOf("/");
        String name = title.substring(index+1,title.length());
        Image_name.setText(name);

        Image.setImageResource(item_pos);

        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_Full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Full_Image.class);
                intent.putExtra("id",item_pos);

                startActivity(intent);

            }
        });

        dialog.show();

    }


}