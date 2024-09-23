package com.example.healthandfitnessapp.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;
import com.example.healthandfitnessapp.common.Utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class User_Profile extends AppCompatActivity {
    TextView nam,email,phone,age,gender,height,weight,des,dob;
    Button btn;
ImageView image;
String uid,na,em,ph,pass,ag,gen,hei,wei,de,Dob,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nam=findViewById(R.id.nam);
        email=findViewById(R.id.em);
        phone=findViewById(R.id.ph);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gen);
        height=findViewById(R.id.hei);
        weight=findViewById(R.id.wei);
        des=findViewById(R.id.des);
        dob=findViewById(R.id.dob);
        btn=findViewById(R.id.btnreg);
        image=findViewById(R.id.imge);
        btn.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Edit_profile.class);
            Bundle bundle = new Bundle();
            bundle.putString("u_id", uid);
            bundle.putString("nam", na);
            bundle.putString("em", em);
            bundle.putString("pass", pass);
            bundle.putString("ph", ph);
            bundle.putString("age", ag);
            bundle.putString("dob", Dob);
            bundle.putString("gen", gen);
            bundle.putString("hei", hei);
            bundle.putString("wei", wei);
            bundle.putString("des", de);
            bundle.putString("pic", img);
            i.putExtras(bundle);

            startActivity(i);

        });

        user_pro();
    }

    void user_pro() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    System.out.println("LOGINDD"+response);
                    String respArr[]= response.trim().split("#");
                    uid=respArr[0];
                    na=respArr[1];
                    em=respArr[2];
                    ph=respArr[3];
                    pass=respArr[4];
                    Dob=respArr[5];
                    ag=respArr[6];
                    hei=respArr[7];
                    wei=respArr[8];
                    gen=respArr[9];
                    de=respArr[10];
                    img=respArr[11];


                    nam.setText(na);
                    email.setText("  EMAIL: "+em);
                    phone.setText("  CONTACT: "+ph);
                    age.setText("  AGE: "+ag);
                    dob.setText("  DATE OF BIRTH: "+Dob);
                    gender.setText("  GENDER: "+gen);
                    height.setText("  HEIGHT: "+hei+" cm");
                    weight.setText("  WEIGHT: "+wei+" kg");
                    des.setText("  ABOUT: "+de);



                    try {
                        byte[] imageAsBytes = Base64.decode(img.getBytes());

                        image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error: " + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String u_id = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "User_profile");
                map.put("id", u_id);
                return map;
            }
        };
        queue.add(request);
    }
}