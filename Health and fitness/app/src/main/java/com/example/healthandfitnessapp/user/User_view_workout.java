package com.example.healthandfitnessapp.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.adaptor.Nutri_adaptor;
import com.example.healthandfitnessapp.adaptor.Workout_adaptor;
import com.example.healthandfitnessapp.common.Healthify_Model;
import com.example.healthandfitnessapp.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class User_view_workout extends AppCompatActivity {
    ListView wlist;
    List<Healthify_Model> arraylist;
    public Workout_adaptor adap;
TextView cal;
String ca;
    int totalCalories = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_workout);
        cal=findViewById(R.id.cal);
        wlist=findViewById(R.id.work);
        arraylist=new ArrayList<Healthify_Model>();
        wlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println("ItemClickItem clicked at position: ");
                String wid=arraylist.get(position).getW_id();
                String nam=arraylist.get(position).getEx_nam();
                String type=arraylist.get(position).getEx_type();
                String diff=arraylist.get(position).getEx_diff();
                String num=arraylist.get(position).getEx_num();
                String set=arraylist.get(position).getEx_set();
                String rea=arraylist.get(position).getEx_res();
                String cal=arraylist.get(position).getCalory();
                String de=arraylist.get(position).getInst();
                String pic=arraylist.get(position).getEx_pic();
                Intent intent=new Intent(getApplicationContext(), User_view_work_details.class);
                Bundle bundle=new Bundle();
                bundle.putString("w_id", wid);
                bundle.putString("e_nam", nam);
                bundle.putString("type", type);
                bundle.putString("diff", diff);
                bundle.putString("numb", num);
                bundle.putString("sets", set);
                bundle.putString("res", rea);
                bundle.putString("cal", cal);
                bundle.putString("des", de);
                bundle.putString("pic", pic);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        view_nutri_plan();
    }

    public void addCalories(int calories) {
        totalCalories += calories;
        cal.setText("Calories Burnt: " + totalCalories + " kcal");
    }

    public void view_nutri_plan(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    String data = response.trim();
                    arraylist = Arrays.asList(gson.fromJson(data, Healthify_Model[].class));
                    adap = new Workout_adaptor(User_view_workout.this, arraylist);
                    wlist.setAdapter(adap);
                    registerForContextMenu(wlist);

                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "ViewWorkOut");
                return map;
            }
        };
        queue.add(request);
    }
}