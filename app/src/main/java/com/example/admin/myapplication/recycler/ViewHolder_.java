package com.example.admin.myapplication.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.R;

public class ViewHolder_ extends RecyclerView.ViewHolder
{
    // A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    public ImageView thumbnail;
    protected ImageView thumbnaill;
    public TextView title;

    public ViewHolder_(View view) // constructor with one argument
    {   super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
      //  this.thumbnaill = (ImageView) view.findViewById(R.id.thumbnaill);
        this.title = (TextView) view.findViewById(R.id.title);
    }

}