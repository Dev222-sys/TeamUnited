package com.example.teamunited.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamunited.Activity.Guest_Login;
import com.example.teamunited.Activity.News;
import com.example.teamunited.Activity.Progress;
import com.example.teamunited.Activity.Reading_tools;
import com.example.teamunited.Activity.Select_Area;
import com.example.teamunited.Adapter.SlidingImageAdapter;
import com.example.teamunited.CheckInternetConnection;
import com.example.teamunited.MainActivity;
import com.example.teamunited.Modal.SliderItem;
import com.example.teamunited.Modal.User_login;
import com.example.teamunited.R;
import com.example.teamunited.SliderAdapterExample;
import com.example.teamunited.storage.SharedPrefManager;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {

    private static ViewPager mpage;
    CirclePageIndicator indicator;
    private static int currentPage=0;
    private static int No_page=0;
    SliderItem ImageSilderModels;
    ArrayList<SliderItem> Image=new ArrayList<>();
    RequestQueue requestQueue;
    private SliderAdapterExample adapter;
    Progress progress;
    TextView audio_tools;
    CardView reading_card;
    RecyclerView parent_recycler_view;
    LinearLayout reading_tool,newstools,video_tool;








    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mpage=view.findViewById(R.id.imageSlider);
        progress=new Progress();
        indicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        parent_recycler_view=view.findViewById(R.id.parent_recycler_view);
        audio_tools=view.findViewById(R.id.audio_tools);
        reading_tool=view.findViewById(R.id.reading_tool);
        newstools=view.findViewById(R.id.newstools);
        video_tool=view.findViewById(R.id.video_tool);
        init();
        reading_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(), Reading_tools.class);
                startActivity(in);

            }
        });
        video_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main,new VideoTools());
                fr.commit();
            }
        });
        newstools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main,new Wallet());
                fr.commit();
            }
        });



        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!SharedPrefManager.getInstance(getActivity()).isLoggedin())
        {
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

        }
    }


    private void init() {
        progress.createdialog(getActivity());
        progress.showpDialog();
        String url="https://prabhattrading.com/apis/banner";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getActivity(), response+"", Toast.LENGTH_SHORT).show();

                try {


                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for (int i=0;i<=jsonArray.length();i++)
                    {
                        ImageSilderModels=new SliderItem();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        final String image=jsonObject1.getString("banner_img");
                        ImageSilderModels.setImageUrl(image);

                        Image.add(ImageSilderModels);

                        mpage.setAdapter(new SlidingImageAdapter(Image,getContext()));
                        indicator.setViewPager(mpage);
                        progress.hidepDialog();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.hidepDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hidepDialog();

                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(5 * density);
        No_page = Image.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == No_page) {
                    currentPage = 0;
                }
                mpage.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    /*@Override
    public void onResume() {
        super.onResume();
        //check Internet Connection

        new CheckInternetConnection(getActivity()).checkConnection();
    }
*/

}



