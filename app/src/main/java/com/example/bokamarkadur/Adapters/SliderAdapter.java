package com.example.bokamarkadur.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.example.bokamarkadur.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public int clickcount=0;
    public SliderAdapter(Context context){
        this.context = context;
    }

    public  int[] slide_images = {

            R.drawable.hieu,
            R.drawable.baldvin,
            R.drawable.sunna,
            R.drawable.thordis
    };

    public String[] slide_heading = {
            "Hieu Van Phan",
            "Baldvin Blær Oddsson",
            "Sunna Björnsdóttir",
            "Þórdís Pétursdóttir"
    };

    public String[] slide_desc = {
            "I'm a 24 years old student of computer science at the University of Iceland. " +
                    "\n"+"My main interest in the field is app and web development. I'm current work in Össur as Silicone Technical.\n"
                    ,
            "I am 35 year old B.Sc. of Electrical and Computer Engineering, now studying Computer Science. My main interest in the field is app and web development. I am studying computer science to gain the skills to develop some of the ideas that I have for integrated apps and websites to for example bring better methods for Personal Development and easier and more fun shopping experience to the masses.",
            "I'm a 27 year old biochemist and soon to be computer scientist. I work in the biotech industry and hope to be able to use my computer skills to aid research and development in biological sciences.\n" +
                    "I am extremely fond of giraffes and ice cream.",
            "I am a 31 years old physical therapist now studying computer science. I’m married and I have two sons. Several years ago I became obsessed with programming. Working with web development would be a dream come true."
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject( View view,  Object object) {
        return view == (RelativeLayout) object;
    }


    @Override
    public Object instantiateItem( ViewGroup container,final int position) {



        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will log the page number that was click
                //and have something fun :D
                Log.i("TAG", "This page was clicked: " + position);
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    //first time clicked to do this
                    Toast.makeText(context.getApplicationContext(),"Takk fyrir að ýtta á mig !", Toast.LENGTH_LONG).show();
                }
                else if (clickcount==5) {
                    Toast.makeText(context.getApplicationContext(),"Til hamingju !", Toast.LENGTH_LONG).show();
                } else {
                    //check how many times clicked and so on
                    Toast.makeText(context.getApplicationContext(),5-clickcount+" sinnum meira !", Toast.LENGTH_LONG).show();
                }
                if (position == 0 && clickcount==5) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                    alertDialog.setTitle("Job Seeking");
                    alertDialog.setMessage("Phone : (+354) 6161350" + "\n" +
                            "https://www.linkedin.com/in/hieu-van-phan/");
                    alertDialog.setIcon(R.drawable.hieu);
                    alertDialog.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do the stuff..
                                    clickcount = 0;
                                }
                            }
                    );
                    clickcount = 0;
                    alertDialog.show();
                } else if ( position == 1 && clickcount==5) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                    alertDialog.setTitle("Job Seeking");
                    alertDialog.setMessage("Phone : (+354) 7742340");
                    alertDialog.setIcon(R.drawable.baldvin);

                    alertDialog.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do the stuff..
                                    clickcount = 0;
                                }
                            }
                    );
                    clickcount = 0;
                    alertDialog.show();
                } else if ( position == 2 && clickcount==5) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                    alertDialog.setTitle("Job Seeking");
                    alertDialog.setMessage("Phone : (+354) 6590792" + "\n" +
                            "https://www.linkedin.com/in/sunnabj/");
                    alertDialog.setIcon(R.drawable.sunna);

                    alertDialog.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do the stuff..
                                    clickcount = 0;
                                }
                            }
                    );
                    clickcount = 0;
                    alertDialog.show();
                } else if ( position == 3 && clickcount==5 ){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                    alertDialog.setTitle("Job Seeking");
                    alertDialog.setMessage("Phone : (+354) ??????" + "\n" +
                            "https://www.linkedin.com/in/thordis-petursdottir/");
                    alertDialog.setIcon(R.drawable.thordis);

                    alertDialog.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do the stuff..
                                    clickcount = 0;
                                }
                            }
                    );
                    clickcount = 0;
                    alertDialog.show();
                }
            }
        });

        ImageView slideImageView = (ImageView) view.findViewById(R.id.imageView);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_Head);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_heading[position]);
        slideDescription.setText((slide_desc[position]));

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }
}
