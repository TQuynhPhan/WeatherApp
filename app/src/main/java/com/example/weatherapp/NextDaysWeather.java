package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NextDaysWeather extends AppCompatActivity {

    String city="";

    ImageView imgBack;
    TextView txtTenTP;
    ListView listView;

    WeatherAdapter adapter;
    ArrayList<Weather>arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_days_weather);

        imgBack=findViewById(R.id.imgBack);
        txtTenTP=findViewById(R.id.txtTenTP);
        listView=findViewById(R.id.listView);
        arr=new ArrayList<Weather>();
        adapter=new WeatherAdapter(NextDaysWeather.this, arr);
        listView.setAdapter(adapter);

        Intent intent=getIntent();
        String cityName=intent.getStringExtra("cityName");
        if(cityName.equals(""))
            city="Saigon";
        else
            city=cityName;
        get7NextDaysWeather(city);
        //Toast.makeText(this, city, Toast.LENGTH_LONG).show();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void get7NextDaysWeather(String data){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=c8413c383657232eefd13c4a954498e9";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("ketqua","JSON:"+response);
                        //Toast.makeText(NextDaysWeather.this, "ketqua"+response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObjectCity=jsonObject.getJSONObject("city");
                            String nameCity=jsonObjectCity.getString("name");
                            txtTenTP.setText(nameCity);

                            JSONArray jsonArray=jsonObject.getJSONArray("list");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObjectList=jsonArray.getJSONObject(i);
                                String jsonDate=jsonObjectList.getString("dt");
                                long lDate=Long.valueOf(jsonDate);
                                Date date=new Date(lDate*1000L); //đổi ra ms
                                SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE yyyy-MM-dd hh:mm");
                                String currentDate=dateFormat.format(date);

                                JSONObject jsonObjectMain=jsonObjectList.getJSONObject("main");
                                String jsonMinTemp=jsonObjectMain.getString("temp_min");
                                String jsonMaxTemp=jsonObjectMain.getString("temp_max");
                                Double dMinTemp=Double.valueOf(jsonMinTemp);
                                String minTemp=String.valueOf(dMinTemp.intValue());
                                Double dMaxTemp=Double.valueOf(jsonMinTemp);
                                String maxTemp=String.valueOf(dMaxTemp.intValue());

                                JSONArray jsonArrayWeather=jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                                String status=jsonObjectWeather.getString("description");
                                String icon=jsonObjectWeather.getString("icon");
                                arr.add(new Weather(currentDate, status, icon, minTemp, maxTemp));

                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
