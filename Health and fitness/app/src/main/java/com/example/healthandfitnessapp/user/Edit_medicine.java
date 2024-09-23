package com.example.healthandfitnessapp.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Edit_medicine extends AppCompatActivity {
    EditText med_nam,dosage,des;
    Spinner med_food;
    CheckBox checkMorning, checkAfternoon, checkNight;
    String m_id,me,dos,food,de,timeofday;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);
        med_nam=findViewById(R.id.nam);
        dosage=findViewById(R.id.dos);
        des=findViewById(R.id.des);
        med_food=findViewById(R.id.food);
        save=findViewById(R.id.btnmed);

        checkMorning = findViewById(R.id.checkMorning);
        checkAfternoon = findViewById(R.id.checkAfternoon);
        checkNight = findViewById(R.id.checkNight);

        String typ[] = {"BEFORE OR AFTER FOOD", "Before Food", "After Food"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, typ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.marcellus);
                    textView.setTypeface(typeface);
                    textView.setTextColor(Color.GRAY);
                    textView.setBackgroundColor(Color.LTGRAY);
                } else {
                    textView.setTextColor(Color.GREEN);
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.marcellus);
                    textView.setTypeface(typeface);
                }
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.marcellus);
                textView.setTypeface(typeface);
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        med_food.setAdapter(arrayAdapter);

        Intent i = getIntent();
        m_id = i.getExtras().getString("m_id");
        me = i.getExtras().getString("med");
        dos = i.getExtras().getString("dos");
        food = i.getExtras().getString("food");
        timeofday = i.getExtras().getString("timeofday");
        de = i.getExtras().getString("des");

        med_nam.setText(me);
        dosage.setText(dos);
        des.setText(de);

        save.setOnClickListener(v -> {
            me=med_nam.getText().toString();
            dos=dosage.getText().toString();
            food=med_food.getSelectedItem().toString();
            de=des.getText().toString();
            StringBuilder timeStringBuilder = new StringBuilder();
            if (checkMorning.isChecked()) {
                timeStringBuilder.append("Morning ");
            }
            if (checkAfternoon.isChecked()) {
                timeStringBuilder.append("Afternoon ");
            }
            if (checkNight.isChecked()) {
                timeStringBuilder.append("Night");
            }

            timeofday = timeStringBuilder.toString();

            if (me.isEmpty()) {
                Snackbar.make(med_nam, "Please Medicine Name", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (dos.isEmpty()) {
                Snackbar.make(dosage, "Please enter Dosage", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (food.isEmpty()) {
                Snackbar.make(med_food, "Please enter Time According to food", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (de.isEmpty()) {
                Snackbar.make(des, "Please enter Instructions", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else {
                UpdateMedicine();
            }
        });
    }

    void UpdateMedicine() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    Toast.makeText(Edit_medicine.this, "Edited successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User_view_medicine.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                String u_id;
                SharedPreferences sh = getApplicationContext().getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                u_id=sh.getString("UID","not_data");
                map.put("requestType", "Edit_medicine");
                map.put("med_id", m_id);
                map.put("user_id", u_id);
                map.put("medname", me);
                map.put("dosage", dos);
                map.put("timeofday", timeofday);
                map.put("food", food);
                map.put("des", de);
                return map;
            }
        };
        queue.add(request);
    }
}
