package com.example.healthandfitnessapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthandfitnessapp.Login;
import com.example.healthandfitnessapp.R;

public class User_home extends AppCompatActivity {
    CardView chat,food,med,work;
    ImageView pro,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        chat=findViewById(R.id.chat);
        food=findViewById(R.id.food);
        work=findViewById(R.id.work);
        med=findViewById(R.id.med);
        pro=findViewById(R.id.pro);
        log=findViewById(R.id.log);
        chat.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Chatbot.class);
            startActivity(i);
        });
        med.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), User_add_medicine.class);
            startActivity(i);
        });
        work.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), User_view_workout.class);
            startActivity(i);
        });
        pro.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), User_Profile.class);
            startActivity(i);
        });
        food.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), User_view_nutriplan.class);
            startActivity(i);
        });
        log.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please logout", Toast.LENGTH_SHORT).show();
    }
}