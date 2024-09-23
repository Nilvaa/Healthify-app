package com.example.healthandfitnessapp.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.healthandfitnessapp.adaptor.Medicine_adaptor;
import com.example.healthandfitnessapp.adaptor.Nutri_adaptor;
import com.example.healthandfitnessapp.common.Healthify_Model;
import com.example.healthandfitnessapp.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class User_view_medicine extends AppCompatActivity {
    ListView list;
    List<Healthify_Model> arraylist;
    public Medicine_adaptor adap;
String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_medicine);
        userId = getIntent().getStringExtra("user_id");
        list=findViewById(R.id.medi);
        arraylist=new ArrayList<Healthify_Model>();
        view_medicine();
    }


    public void view_medicine(){
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
                    adap = new Medicine_adaptor(User_view_medicine.this, arraylist);
                    list.setAdapter(adap);
                    registerForContextMenu(list);

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
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String u_id = sharedPreferences.getString("UID", "");
                map.put("requestType", "ViewMedicine");
                map.put("user_id", u_id);
                return map;
            }
        };
        queue.add(request);
    }
}