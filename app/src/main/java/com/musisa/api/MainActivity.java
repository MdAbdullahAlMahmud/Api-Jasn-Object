package com.musisa.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView tmp,descrip,wind,name;
    ImageView weathermood;
    String url="https://api.openweathermap.org/data/2.5/weather?q=dhaka,bd&appid=cd3ba49f4f916603094f4e1afbe6eeb5&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmp= findViewById(R.id.temp);
        descrip= findViewById(R.id.description);
        wind= findViewById(R.id.windspeed);
        name= findViewById(R.id.cityname);
        weathermood= findViewById(R.id.weathercondition);


         requestQueue=Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= (JSONObject) response.getJSONArray("weather").get(0);

                    descrip.setText(jsonObject.getString("description"));

                    //temp

                    String t = response.getJSONObject("main").getString("temp");
                    tmp.setText(t+" °C");
                    //wind speed

                    String speed= response.getJSONObject("wind").getString("speed");
                    String kmph= " km/h";
                    String s= "Wind Speed ";
                    wind.setText(s+ speed+ kmph);

                    //city
                    String cityname= response.getString("name");
                    name.setText(cityname);

                    Glide.with(getApplicationContext())
                            .load("https://ssl.gstatic.com/onebox/weather/64/partly_cloudy.png")
                            .into(weathermood);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check Net Connection", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
