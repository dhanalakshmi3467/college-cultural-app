package com.example.today;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends Fragment  {

    Button day1;
    Button day2;
    Button day3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        day1 = (Button) view.findViewById(R.id.day1Btn);
        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  FragmentTransaction t = this.getFragmentManager().beginTransaction();
                Fragment mFrag = new MyFragment();
                t.replace(R.id.content_main, mFrag);
                t.commit();*/
               Day1 day1 = new Day1();
                getActivity().getSupportFragmentManager().beginTransaction()
                       .replace(R.id.content_main,day1,day1.getTag()).commit();
               /* Intent intent = new Intent(getContext(), Day1.class);
               // intent.putExtra("YOUR_DATA_KEY", data);
                startActivity(intent);*/
            }
        });

       /* day2 = (Button) view.findViewById(R.id.day2Btn);
        day2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                Day2 day2 = new Day2();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main,day2,day2.getTag()).commit();
            }
        });

        day3 = (Button) view.findViewById(R.id.day2Btn);
        day3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Day3 day3 = new Day3();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main,day3,day3.getTag()).commit();
            }
        });
*/
        return view;
    }


}





//startActivity(new Intent(getContext(),Day1.class));

                /*
                * NextFragment nextFrag= new NextFragment();
getActivity().getSupportFragmentManager().beginTransaction()
             .replace(R.id.Layout_container, nextFrag, "findThisFragment")
             .addToBackStack(null)
             .commit();
                *
                * */





 /*
 public void day1(View view){
     switch(view.getId()) {
         case R.id.day1Btn:
         *//*    Intent myIntent = new Intent();
             myIntent.setClassName(your_package_name_string, your_activity_name_string);
             // for ex: your package name can be "com.example"
             // your activity name will be "com.example.Contact_Developer"
             startActivity(myIntent);*//*
             startActivity(new Intent(getContext(),Day1.class));
             break;
     }
 }*/