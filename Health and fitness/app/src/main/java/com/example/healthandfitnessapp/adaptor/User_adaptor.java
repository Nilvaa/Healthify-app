package com.example.healthandfitnessapp.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;
import com.example.healthandfitnessapp.common.Healthify_Model;

import java.io.IOException;
import java.util.List;

public class User_adaptor extends ArrayAdapter<Healthify_Model> {

    Activity context;
    List<Healthify_Model> list;

    public User_adaptor(Activity context, List<Healthify_Model> list) {
        super(context, R.layout.activity_user_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_user_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.age);
        TextView ph=view.findViewById(R.id.phn);
        ImageView im=view.findViewById(R.id.imge);
        na.setText("NAME: "+list.get(position).getUser_name());
        em.setText("AGE: "+list.get(position).getU_age());
        ph.setText("CONTACT: "+list.get(position).getU_phone());


        String image=list.get(position).getU_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}