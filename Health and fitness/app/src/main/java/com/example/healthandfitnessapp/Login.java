package com.example.healthandfitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.admin.Admin_home;
import com.example.healthandfitnessapp.common.Utility;
import com.example.healthandfitnessapp.user.User_home;
import com.example.healthandfitnessapp.user.User_register;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText username,passwd;
    Button signin;
    TextView tv;
    String un,pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.l_uname);
        passwd=findViewById(R.id.l_pass);
        tv=findViewById(R.id.tv_log);
        signin=findViewById(R.id.btnlog);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.POST_NOTIFICATIONS,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE
        };
        if (!hasPermission(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        signin.setOnClickListener(v -> {
            un=username.getText().toString();
            pas=passwd.getText().toString();

            if (un.isEmpty()) {
                Snackbar.make(username, "Please Enter Username", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (pas.isEmpty()) {
                Snackbar.make(passwd, "Please Enter Password", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else {
                login();
            }

        });
        tv.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), User_register.class);
            startActivity(i);
        });
    }
    public static boolean hasPermission(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    void login() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    System.out.println("LOGINDD"+response);
                    String respArr[]= response.trim().split("#");
                    String uid=respArr[0];
                    String type=respArr[1];
                    System.out.println(uid);
                    System.out.println(type);
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa"+respArr[1]);
                    SharedPreferences.Editor editor = getSharedPreferences("profileprefer", MODE_PRIVATE).edit();
                    editor.putString("UID",uid);
                    editor.putString("type",type);
                    editor.commit();
                    if(type.equals("admin")){
                        startActivity(new Intent(getApplicationContext(), Admin_home.class));

                    }else  if(type.equals("staff")) {
//                        startActivity(new Intent(getApplicationContext(), Staff_navActivity.class));

                    }else  if(type.equals("user")) {
                        startActivity(new Intent(getApplicationContext(), User_home.class));
                    }
                    else{
                        Toast.makeText(Login.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Failed" , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "My Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "login");
                map.put("uname", un);
                map.put("pass", pas);
                return map;
            }
        };
        queue.add(request);
    }
}