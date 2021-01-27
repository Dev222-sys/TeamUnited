package com.example.teamunited.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.teamunited.Activity.IntroActivity;
import com.example.teamunited.R;

import java.util.Locale;

public class profile extends Fragment {

    LinearLayout ll_change_language;

    public profile() {

    }

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        initview(view);
        loadlocale();




        return view;
    }

    private void initview(View view) {
        ll_change_language=view.findViewById(R.id.ll_change_language);


        ll_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changelanguage();

            }
        });



    }
    public void changelanguage()
    {
        String[]list_item={"English",
                "हिंदी"};



        // set dialog message


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("CHOOSE  LANGUAGE");
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setCancelable(false);

        builder.setSingleChoiceItems(list_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0)
                {
                    setLocale("en");
                    getActivity().recreate();

                }else if(i==1)
                {
                    setLocale("hi");
                    getActivity().recreate();


                }
                dialogInterface.dismiss();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }


    public void setLocale(String lan) {
        Locale locale=new Locale(lan);
        locale.setDefault(locale);

        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration,getActivity().getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getActivity().getSharedPreferences("setting",getActivity().MODE_PRIVATE).edit();
        editor.putString("My_Lang",lan);
        editor.apply();

    }
    public void loadlocale()
    {
        SharedPreferences pref=getActivity().getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String language=pref.getString("My_Lang","");

        setLocale(language);


    }
}