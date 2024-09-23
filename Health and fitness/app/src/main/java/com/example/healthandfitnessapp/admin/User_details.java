package com.example.healthandfitnessapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;

import java.io.IOException;

public class User_details extends AppCompatActivity {
    TextView nam,email,phone,age,gender,height,weight,des,dob;
    ImageView image;
    String uid,na,em,ph,pass,ag,gen,hei,wei,de,Dob,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        nam=findViewById(R.id.nam);
        email=findViewById(R.id.em);
        phone=findViewById(R.id.ph);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gen);
        height=findViewById(R.id.hei);
        weight=findViewById(R.id.wei);
        des=findViewById(R.id.des);
        dob=findViewById(R.id.dob);
        image=findViewById(R.id.imge);

        Intent i = getIntent();
        uid = i.getExtras().getString("u_id");
        na = i.getExtras().getString("nam");
        em = i.getExtras().getString("em");
        ph = i.getExtras().getString("ph");
        Dob = i.getExtras().getString("dob");
        ag = i.getExtras().getString("age");
        gen = i.getExtras().getString("gen");
        hei = i.getExtras().getString("hei");
        wei = i.getExtras().getString("wei");
        de = i.getExtras().getString("des");
        img = i.getExtras().getString("img");

        nam.setText(na);
        email.setText("EMAIL: "+em);
        phone.setText("CONTACT: "+ph);
        dob.setText("DATE OF BIRTH: "+Dob);
        age.setText("AGE: "+ag);
        gender.setText("GENDER: "+gen);
        height.setText("HEIGHT: "+hei+" cm");
        weight.setText("WEIGHT: "+wei+" kg");
        des.setText("ABOUT: "+de);

        try {
            byte[] imageAsBytes = Base64.decode(img.getBytes());

            image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}