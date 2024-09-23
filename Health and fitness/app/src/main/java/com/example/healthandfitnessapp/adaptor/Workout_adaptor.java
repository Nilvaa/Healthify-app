package com.example.healthandfitnessapp.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;
import com.example.healthandfitnessapp.common.Healthify_Model;
import com.example.healthandfitnessapp.user.User_view_work_details;
import com.example.healthandfitnessapp.user.User_view_workout;

import java.io.IOException;
import java.util.List;

public class Workout_adaptor extends ArrayAdapter<Healthify_Model> {

    Activity context;
    List<Healthify_Model> list;
    Button done;
    CardView card;
    public Workout_adaptor(Activity context, List<Healthify_Model> list) {
        super(context, R.layout.activity_workout_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_workout_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.type);
        TextView ca=view.findViewById(R.id.cal);
        done=view.findViewById(R.id.btndon);
        card=view.findViewById(R.id.card);
        ImageView im=view.findViewById(R.id.imgei);
        na.setText(list.get(position).getEx_nam());
        em.setText(list.get(position).getEx_diff());
        ca.setText("Calories Burned: "+list.get(position).getCalory()+" kcal");
        done.setOnClickListener(v -> {
            int calories = Integer.parseInt(list.get(position).getCalory());
            ((User_view_workout) context).addCalories(calories);
        });

        String image=list.get(position).getEx_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        card.setOnClickListener(v -> {
            String wid=list.get(position).getW_id();
            String nam=list.get(position).getEx_nam();
            String type=list.get(position).getEx_type();
            String diff=list.get(position).getEx_diff();
            String num=list.get(position).getEx_num();
            String set=list.get(position).getEx_set();
            String rea=list.get(position).getEx_res();
            String cal=list.get(position).getCalory();
            String de=list.get(position).getInst();
            String pic=list.get(position).getEx_pic();
            Intent intent=new Intent(getContext(), User_view_work_details.class);
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
            getContext().startActivity(intent);
        });
        return view;
    }
}