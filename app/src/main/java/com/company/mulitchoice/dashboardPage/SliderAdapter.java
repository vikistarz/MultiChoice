package com.company.mulitchoice.dashboardPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.mulitchoice.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private Context myContext;
    private ArrayList<SliderData> mySliderData;

    public SliderAdapter(Context myContext, ArrayList<SliderData> mySliderData) {
        this.myContext = myContext;
        this.mySliderData = mySliderData;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater myLayoutInflater = LayoutInflater.from(parent.getContext());
        View myView = myLayoutInflater.inflate(R.layout.slider_layout, parent, false);
        return new SliderAdapterViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {
        SliderData mySliderList = mySliderData.get(position);
        viewHolder.myImage.setImageResource(mySliderList.getPictures());
        viewHolder.course.setText(mySliderList.getCourse());
        viewHolder.title.setText(mySliderList.getTools());
        viewHolder.title2.setText(mySliderList.getTools2());

    }

    @Override
    public int getCount() {
        return mySliderData.size();
    }

    public class SliderAdapterViewHolder extends ViewHolder {

        private ImageView myImage;
        private TextView course;
        private TextView title, title2;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);

            myImage = itemView.findViewById(R.id.my_image);
            course = itemView.findViewById(R.id.tv_course);
            title = itemView.findViewById(R.id.tv_title);
            title2 = itemView.findViewById(R.id.tv_title_two);
        }
    }
}

