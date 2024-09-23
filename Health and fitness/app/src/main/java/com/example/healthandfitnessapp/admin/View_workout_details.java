package com.example.healthandfitnessapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;

import java.io.IOException;

public class View_workout_details extends AppCompatActivity {
    TextView ex_nam, num, sets, rest, calory, instr,type, diff;
    String wid,nam,nu, set, res, cal, ins, ty, pic,dif;
    ImageView img;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout_details);
        img=findViewById(R.id.imgei);
        ex_nam=findViewById(R.id.nam);
        num=findViewById(R.id.num);
        sets=findViewById(R.id.set);
        type=findViewById(R.id.typ);
        diff=findViewById(R.id.diff);
        rest=findViewById(R.id.res);
        calory=findViewById(R.id.cal);
        instr=findViewById(R.id.ins);
        edit=findViewById(R.id.btnplan);

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

        edit.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), Edit_workout.class);
            Bundle bundle=new Bundle();
            bundle.putString("w_id", wid);
            bundle.putString("e_nam", nam);
            bundle.putString("type", ty);
            bundle.putString("diff", dif);
            bundle.putString("numb", nu);
            bundle.putString("sets", set);
            bundle.putString("res", res);
            bundle.putString("cal", cal);
            bundle.putString("des", ins);
            bundle.putString("pic", pic);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        try {
            byte[] imageAsBytes = Base64.decode(pic.getBytes());

            img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}