package com.example.healthandfitnessapp.admin;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.adaptor.Admin_workout_adaptor;
import com.example.healthandfitnessapp.adaptor.Nutri_adaptor;
import com.example.healthandfitnessapp.common.Healthify_Model;
import com.example.healthandfitnessapp.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class Admin_view_workout extends AppCompatActivity {
    ListView list;
    List<Healthify_Model> arraylist;
    public Admin_workout_adaptor adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_workout);
        list=findViewById(R.id.work);
        arraylist=new ArrayList<Healthify_Model>();

        view_workout();
    }


    public void view_workout(){
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
                    adap = new Admin_workout_adaptor(Admin_view_workout.this, arraylist);
                    list.setAdapter(adap);
                    registerForContextMenu(list);

                } else {
                    Toast.makeText(getApplicationContext(), "No plans found", Toast.LENGTH_LONG).show();
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
                map.put("requestType", "AdminWorkOutPlan");
                return map;
            }
        };
        queue.add(request);
    }
}