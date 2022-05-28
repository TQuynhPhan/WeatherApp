package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    EditText edtViTri;
    Button btnXacNhan, btnNgayTiep;
    TextView txtTP, txtQG, txtNgayHienTai, txtNhietDo, txtTrangThai, txtDoAm, txtMua, txtGio;
    ImageView imgWeather;
    String CityName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtViTri=findViewById(R.id.edtViTri);
        btnXacNhan=findViewById(R.id.btnXacNhan);
        btnNgayTiep=findViewById(R.id.btnNgayTiep);
        txtTP=findViewById(R.id.txtTP);
        txtQG=findViewById(R.id.txtQG);
        txtNgayHienTai=findViewById(R.id.txtNgayHienTai);
        txtNhietDo=findViewById(R.id.txtNhietDo);
        txtTrangThai=findViewById(R.id.txtTrangThai);
        txtDoAm=findViewById(R.id.txtDoAm);
        txtMua=findViewById(R.id.txtMua);
        txtGio=findViewById(R.id.txtGio);
        imgWeather=findViewById(R.id.imgWeather);

        getCurrentWeatherData("Saigon");

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=edtViTri.getText().toString();
                if(city.equals(""))
                    CityName="Saigon";
                else
                    CityName=city;
                getCurrentWeatherData(CityName);
            }
        });

        btnNgayTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=edtViTri.getText().toString();
                //Toast.makeText(MainActivity.this, city, Toast.LENGTH_LONG).show();
//                if(cityName.equals(""))
//                    cityName="Saigon";
                Intent intent=new Intent(MainActivity.this, NextDaysWeather.class);
                intent.putExtra("cityName", city);
                startActivity(intent);
            }
        });
    }

    public void getCurrentWeatherData(String data){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=c8413c383657232eefd13c4a954498e9";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String jsonDate=jsonObject.getString("dt");
                            String cityName=jsonObject.getString("name");
                            txtTP.setText(cityName);

                            long lDate=Long.valueOf(jsonDate);
                            Date date=new Date(lDate*1000L); //đổi ra ms
                            SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm");
                            String currentDate=dateFormat.format(date);
                            txtNgayHienTai.setText(currentDate);

                            JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0); //lấy 1 phần tử đầu tiên
                            String status=jsonObjectWeather.getString("main");
                            String icon=jsonObjectWeather.getString("icon");

                            Picasso.get().load("https://openweathermap.org/img/wn/"+icon+".png").into(imgWeather);
                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain=jsonObject.getJSONObject("main");
                            String jsonTemp=jsonObjectMain.getString("temp");
                            String humidity=jsonObjectMain.getString("humidity");

                            Double dTemp=Double.valueOf(jsonTemp);
                            String temp=String.valueOf(dTemp.intValue());
                            txtNhietDo.setText(temp + "C");
                            txtDoAm.setText(humidity+"%");

                            JSONObject jsonObjectWind=jsonObject.getJSONObject("wind");
                            String wind=jsonObjectWind.getString("speed");
                            txtGio.setText(wind+"m/s");

                            JSONObject jsonObjectCloud=jsonObject.getJSONObject("clouds");
                            String cloud=jsonObjectCloud.getString("all");
                            txtMua.setText(cloud+"%");

                            JSONObject jsonObjectSys=jsonObject.getJSONObject("sys");
                            String country=jsonObjectSys.getString("country");
                            txtQG.setText(country);

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