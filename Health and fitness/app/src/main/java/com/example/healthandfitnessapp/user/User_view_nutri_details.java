package com.example.healthandfitnessapp.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;

import java.io.IOException;

public class User_view_nutri_details extends AppCompatActivity {
    String pna,bre,lun,din,sna,wat,mac,nid,ty,img,de;
    ImageView image;
    TextView plan_name,breakfast,lunch,type,dinner,snack,water,macro,des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_nutri_details);
        image=findViewById(R.id.imgei);
        plan_name=findViewById(R.id.nam);
        breakfast=findViewById(R.id.bre);
        lunch=findViewById(R.id.lun);
        dinner=findViewById(R.id.din);
        snack=findViewById(R.id.sna);
        water=findViewById(R.id.wat);
        type=findViewById(R.id.typ);
        macro=findViewById(R.id.macro);
        des=findViewById(R.id.des);
        Intent i = getIntent();
        nid = i.getExtras().getString("nu_id");
        pna = i.getExtras().getString("p_nam");
        ty = i.getExtras().getString("type");
        bre = i.getExtras().getString("break");
        lun = i.getExtras().getString("lunch");
        din = i.getExtras().getString("dinner");
        sna = i.getExtras().getString("snack");
        wat = i.getExtras().getString("water");
        mac = i.getExtras().getString("macro");
        de = i.getExtras().getString("des");
        img = i.getExtras().getString("pic");
        plan_name.setText(pna);
        type.setText("TYPE: "+ty);
        breakfast.setText("BREAKFAST: "+bre);
        lunch.setText("LUNCH: "+lun);
        dinner.setText("DINNER: "+din);
        snack.setText("SNACK: "+sna);
        water.setText("WATER: "+wat+" Litre per day");
        macro.setText("MACRONUTRIENTS: "+mac);
        des.setText("DESCRIPTION: "+de);

        try {
            byte[] imageAsBytes = Base64.decode(img.getBytes());

            image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}