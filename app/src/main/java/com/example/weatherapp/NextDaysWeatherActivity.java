package com.example.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NextDaysWeatherActivity extends Activity {

    ImageView imgBack;
    TextView txtTenTP;
    ListView listView;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_days_weather);

        imgBack=findViewById(R.id.imgBack);
        txtTenTP=findViewById(R.id.txtTenTP);
        listView=findViewById(R.id.listView);

        Intent intent=getIntent();
        String cityName=intent.getStringExtra("cityName");
        Log.d("ketqua","du lieu truyen qua:"+cityName);
        if(cityName.equals(""))
            city="Saigon";
        else
            city=cityName;


        get7NextDaysWeather(city);
    }

    public void get7NextDaysWeather(String data){
        RequestQueue requestQueue= Volley.newRequestQueue(NextDaysWeatherActivity.this);
        String url="https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=c8413c383657232eefd13c4a954498e9";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua","JSON:"+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
