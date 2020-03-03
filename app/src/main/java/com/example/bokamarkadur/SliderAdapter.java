package com.example.bokamarkadur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

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
            "Hi, my name is Hieu. I'm a 24 years old student of computer science at the University of Iceland. My main interest in the field is app and web development. I'm current work in Össur as Silicone Technical.\n" +
                    "\n",
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
    public Object instantiateItem( ViewGroup container, int position) {



        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);

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
