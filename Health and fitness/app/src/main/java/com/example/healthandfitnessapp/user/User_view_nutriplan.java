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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.adaptor.Nutri_adaptor;
import com.example.healthandfitnessapp.common.Healthify_Model;
import com.example.healthandfitnessapp.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class User_view_nutriplan extends AppCompatActivity {
    ListView list;
    List<Healthify_Model> arraylist;
    public Nutri_adaptor adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_nutriplan);
        list=findViewById(R.id.nutri);
        arraylist=new ArrayList<Healthify_Model>();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String nid=arraylist.get(position).getNu_id();
                String nam=arraylist.get(position).getPlan_nam();
                String type=arraylist.get(position).getPlan_typ();
                String bre=arraylist.get(position).getBreakfast();
                String lun=arraylist.get(position).getLunch();
                String din=arraylist.get(position).getDinner();
                String sna=arraylist.get(position).getSnack();
                System.out.println("snackkk:"+sna);
                String wat=arraylist.get(position).getWater();
                String mac=arraylist.get(position).getMacro();
                String de=arraylist.get(position).getPlan_des();
                String pic=arraylist.get(position).getPlan_pic();
                Intent intent=new Intent(getApplicationContext(), User_view_nutri_details.class);
                Bundle bundle=new Bundle();
                bundle.putString("nu_id", nid);
                bundle.putString("p_nam", nam);
                bundle.putString("type", type);
                bundle.putString("break", bre);
                bundle.putString("lunch", lun);
                bundle.putString("dinner", din);
                bundle.putString("snack", sna);
                bundle.putString("water", wat);
                bundle.putString("macro", mac);
                bundle.putString("des", de);
                bundle.putString("pic", pic);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        view_nutri_plan();
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
                    adap = new Nutri_adaptor(User_view_nutriplan.this, arraylist);
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
                map.put("requestType", "ViewNutriPlan");
                return map;
            }
        };
        queue.add(request);
    }
}