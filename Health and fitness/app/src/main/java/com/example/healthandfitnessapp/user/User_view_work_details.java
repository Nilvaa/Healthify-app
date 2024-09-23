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

public class User_view_work_details extends AppCompatActivity {
    TextView ex_nam, num, sets, rest, calory, instr,type, diff;
    String wid,nam,nu, set, res, cal, ins, ty, pic,dif;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_work_details);
        img=findViewById(R.id.imgei);
        ex_nam=findViewById(R.id.nam);
        num=findViewById(R.id.num);
        sets=findViewById(R.id.set);
        type=findViewById(R.id.typ);
        diff=findViewById(R.id.diff);
        rest=findViewById(R.id.res);
        calory=findViewById(R.id.cal);
        instr=findViewById(R.id.ins);

        Intent i = getIntent();
        wid = i.getExtras().getString("w_id");
        nam = i.getExtras().getString("e_nam");
        ty = i.getExtras().getString("type");
        nu = i.getExtras().getString("numb");
        set = i.getExtras().getString("sets");
        res = i.getExtras().getString("res");
        cal = i.getExtras().getString("cal");
        ins = i.getExtras().getString("des");
        pic = i.getExtras().getString("pic");
        dif = i.getExtras().getString("diff");

        ex_nam.setText(nam);
        type.setText("TYPE: "+ty);
        diff.setText("LEVEL:"+dif);
        num.setText("NUMBER OF TIMES:"+nu);
        sets.setText("SETS:"+set+" set");
        rest.setText("REST PERIOD:"+res);
        calory.setText("CALORIES BURNED:"+cal+" kcal");
        instr.setText("INSTRUCTIONS:"+ins);

        try {
            byte[] imageAsBytes = Base64.decode(pic.getBytes());

            img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}