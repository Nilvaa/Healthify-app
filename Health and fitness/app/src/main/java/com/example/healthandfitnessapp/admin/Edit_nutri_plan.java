package com.example.healthandfitnessapp.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthandfitnessapp.R;
import com.example.healthandfitnessapp.common.Base64;
import com.example.healthandfitnessapp.common.Utility;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Edit_nutri_plan extends AppCompatActivity {
    EditText plan_name,breakfast,lunch,dinner,snack,water,macro,des;
    Spinner planTYpe;
    Button save;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    private Bitmap bitmap;
    String bal = "",MAX;
    Uri imageUri;
    ImageView img,camera,gallery,rec;
    String nid,pna,bre,lun,din,pic,sna,wat,mac,ty,de;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nutri_plan);
        plan_name=findViewById(R.id.p_nam);
        breakfast=findViewById(R.id.bre);
        lunch=findViewById(R.id.lunch);
        dinner=findViewById(R.id.dinn);
        snack=findViewById(R.id.snack);
        water=findViewById(R.id.water);
        macro=findViewById(R.id.macro);
        des=findViewById(R.id.des);
        lunch=findViewById(R.id.lunch);
        planTYpe=findViewById(R.id.type);
        save=findViewById(R.id.btnplan);
        img=findViewById(R.id.add_image);
        camera=findViewById(R.id.cam);
        gallery=findViewById(R.id.gal);
        Intent i = getIntent();
        nid = i.getExtras().getString("nu_id");
        pna = i.getExtras().getString("p_nam");
        ty = i.getExtras().getString("type");
        bre = i.getExtras().getString("break");
        lun = i.getExtras().getString("lunch");
        din = i.getExtras().getString("dinner");
        sna = i.getExtras().getString("snack");
        wat = i.getExtras().getString("water");
        mac = i.getExtras().getString("macro");
        de = i.getExtras().getString("des");
        pic = i.getExtras().getString("pic");
        plan_name.setText(pna);
        breakfast.setText(bre);
        lunch.setText(lun);
        dinner.setText(din);
        snack.setText(sna);
        water.setText(wat);
        macro.setText(mac);
        des.setText(de);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                String fileName = "new-photo-name.jpg";
                //create parameters for Intent with filename
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)\Context.
                imageUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //create new Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, PICK_Camera_IMAGE);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub
                try {
                    Intent gintent = new Intent();
                    gintent.setType("image/*");
                    gintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gintent, "Select Picture"), PICK_IMAGE);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });

        String ptype[]={"TYPE","vegetarian","non-vegetarian"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ptype) {
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
        planTYpe.setAdapter(arrayAdapter);

        save.setOnClickListener(v -> {
            pna=plan_name.getText().toString();
            bre=breakfast.getText().toString();
            lun=lunch.getText().toString();
            din=dinner.getText().toString();
            sna=snack.getText().toString();
            wat=water.getText().toString();
            mac=macro.getText().toString();
            de=des.getText().toString();
            ty=planTYpe.getSelectedItem().toString();
            edit_plan();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image

                    selectedImageUri = imageUri;
		 		    	/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp/=2;
            height_tmp/=2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        img.setImageBitmap(bitmap);

        //...
        Base64.InputStream is;
        BitmapFactory.Options bfo;
        Bitmap bitmapOrg;
        ByteArrayOutputStream bao;

        bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);

        bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        bal = Base64.encodeBytes(ba);


        //..

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = null;
        try {
            cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

   void edit_plan() {
       com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
       StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
           @Override

           public void onResponse(String response) {

               if (!response.trim().equals("failed")) {
                   Toast.makeText(Edit_nutri_plan.this, "Edited Plan successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Admin_view_nutriplan.class));
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
               map.put("requestType", "EditNutriPlan");
               map.put("nutri_id", nid);
               map.put("plan_name", pna);
               map.put("type", ty);
               map.put("breakfast", bre);
               map.put("lunch", lun);
               map.put("dinner", din);
               map.put("water", wat);
               map.put("snack", sna);
               map.put("macro", mac);
               map.put("des", de);
               map.put("image",bal);
               return map;
           }
       };
       queue.add(request);
   }
}