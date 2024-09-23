package com.example.healthandfitnessapp.adaptor;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import com.example.healthandfitnessapp.user.Edit_medicine;
import com.example.healthandfitnessapp.user.User_notification;
import com.example.healthandfitnessapp.user.User_view_nutri_details;
import com.example.healthandfitnessapp.user.User_view_workout;

import java.io.IOException;
import java.util.List;

public class Medicine_adaptor extends ArrayAdapter<Healthify_Model> {

    Activity context;
    List<Healthify_Model> list;
    Button edit,rem;
    String me,dos,food,med,des,timeofday;
    public Medicine_adaptor(Activity context, List<Healthify_Model> list) {
        super(context, R.layout.activity_medicine_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_medicine_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.dos);
        TextView ca=view.findViewById(R.id.timday);
        TextView fo=view.findViewById(R.id.food);
        TextView de=view.findViewById(R.id.des);
        edit=view.findViewById(R.id.btn_edit);
        rem=view.findViewById(R.id.btn_rem);

        na.setText("Medicine Name: "+list.get(position).getMed_nam());
        em.setText("Dosage: "+list.get(position).getDosage());
        ca.setText("Time of day: "+list.get(position).getTimeofday());
        fo.setText("Consume: "+list.get(position).getFood());
        de.setText("Instructions: "+list.get(position).getMed_des());

        edit.setOnClickListener(v -> {
            me = list.get(position).getM_id();
            med=list.get(position).getMed_nam();
            dos=list.get(position).getDosage();
            food = list.get(position).getFood();
            des=list.get(position).getMed_des();
            timeofday=list.get(position).getTimeofday();
            Intent intent=new Intent(getContext(), Edit_medicine.class);
            Bundle bundle=new Bundle();
            bundle.putString("m_id", me);
            bundle.putString("med", med);
            bundle.putString("dos", dos);
            bundle.putString("food", food);
            bundle.putString("des", des);
            bundle.putString("timeofday", timeofday);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        rem.setOnClickListener(v -> {
            sendLocalNotification("Please take you medicine " + list.get(position).getMed_nam() +" "+ list.get(position).getFood(), "Thank you");
        });

        return view;
    }
    private void sendLocalNotification(String title, String message) {
        // Create an intent to open the Stud_notification activity
        Intent intent = new Intent(context, User_notification.class);
        PendingIntent pendingIntent = getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.launch) // Set your notification icon
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Your Channel Name"; // Change this to your desired channel name
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("channel_id", name, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Notify with a unique ID
        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());
    }
}