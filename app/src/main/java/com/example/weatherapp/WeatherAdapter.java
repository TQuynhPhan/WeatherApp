package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {

    Context context;
    ArrayList<Weather>arr;

    public WeatherAdapter(Context context, ArrayList<Weather> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.day_weather_item,null);

        Weather weather=arr.get(i);
        TextView txtNgay=view.findViewById(R.id.txtNgay);
        TextView txtTrangThaiNgay=view.findViewById(R.id.txtTrangThaiNgay);
        TextView txtTempMin=view.findViewById(R.id.txtTempMin);
        TextView txtTempMax=view.findViewById(R.id.txtTempMax);
        ImageView imgIcon=view.findViewById(R.id.imgIcon);

        txtNgay.setText(weather.getDay());
        txtTrangThaiNgay.setText(weather.getStatus());
        txtTempMin.setText(weather.getMinTemp()+"C");
        txtTempMax.setText(weather.getMaxTemp()+"C");
        Picasso.get().load("https://openweathermap.org/img/wn/"+weather.getImgIcon()+".png").into(imgIcon);

        return view;
    }
}
