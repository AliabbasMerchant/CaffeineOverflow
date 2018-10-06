package com.example.sanidhya.m_xpress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddIssueActivity extends AppCompatActivity {

    Bitmap bitmap;
    ImageButton cameraButton;
    Button postButton;
    ImageView mImageView;
    private static final String TAG = "AddIssueActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);

        postButton = findViewById(R.id.button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddIssueActivity.this, "Successfully Posted!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void onClickPost(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = Constants.FEED_URL; // TODO
        StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e(TAG, "onResponse: " + response);
        }, error -> Log.e(TAG, "onResponse: " + "This is not working"));
        queue.add(sr);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView = findViewById(R.id.imageView);
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    public void uploadUserImage(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i(TAG,""+response);
//                Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

            }
        }, (Response.ErrorListener) error -> {
            Log.i(TAG, String.valueOf(error));
            Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                String images = getStringImage(bitmap);
                Log.i(TAG,""+images);
                param.put("image",images);
                return param;
            }
        };

        requestQueue.add(stringRequest);


    }


    public String getStringImage(Bitmap bitmap){
        Log.i(TAG, String.valueOf(bitmap));
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
