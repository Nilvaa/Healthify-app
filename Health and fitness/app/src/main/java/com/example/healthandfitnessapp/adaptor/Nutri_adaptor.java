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

public class Nutri_adaptor extends ArrayAdapter<Healthify_Model> {

    Activity context;
    List<Healthify_Model> list;

    public Nutri_adaptor(Activity context, List<Healthify_Model> list) {
        super(context, R.layout.activity_nutri_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_nutri_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.type);
        ImageView im=view.findViewById(R.id.imgei);
        na.setText(list.get(position).getPlan_nam());
        em.setText(list.get(position).getPlan_typ());


        String image=list.get(position).getPlan_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}