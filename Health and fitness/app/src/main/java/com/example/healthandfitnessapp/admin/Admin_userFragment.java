package com.example.healthandfitnessapp.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.adaptor.User_adaptor;
import com.example.healthandfitnessapp.common.Healthify_Model;
import com.example.healthandfitnessapp.common.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Admin_userFragment extends Fragment {
    ListView list;
    List<Healthify_Model> arraylist;
    public User_adaptor adap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root= inflater.inflate(R.layout.fragment_admin_user, container, false);
        list=root.findViewById(R.id.users);
        arraylist=new ArrayList<Healthify_Model>();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String did=arraylist.get(position).getR_uid();
                String na=arraylist.get(position).getUser_name();
                String em=arraylist.get(position).getU_email();
                String ph=arraylist.get(position).getU_phone();
                String dob=arraylist.get(position).getU_dob();
                String li=arraylist.get(position).getU_age();
                String ar=arraylist.get(position).getU_gen();
                String ho=arraylist.get(position).getU_hei();
                String ab=arraylist.get(position).getU_wei();
                String ex=arraylist.get(position).getU_des();
                String img=arraylist.get(position).getU_pic();
                Intent intent=new Intent(getContext(), User_details.class);
                Bundle bundle=new Bundle();
                bundle.putString("u_id", did);
                bundle.putString("nam", na);
                bundle.putString("em", em);
                bundle.putString("ph", ph);
                bundle.putString("dob", dob);
                bundle.putString("age", li);
                bundle.putString("gen", ar);
                bundle.putString("hei", ho);
                bundle.putString("wei", ab);
                bundle.putString("des", ex);
                bundle.putString("img", img);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        admin_view_users();


        return root;
    }

    public void admin_view_users(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    String data = response.trim();
                    arraylist = Arrays.asList(gson.fromJson(data, Healthify_Model[].class));
                    adap = new User_adaptor(requireActivity(), arraylist);
                    list.setAdapter(adap);
                    registerForContextMenu(list);

                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "ViewUsers");
                return map;
            }
        };
        queue.add(request);
    }
}