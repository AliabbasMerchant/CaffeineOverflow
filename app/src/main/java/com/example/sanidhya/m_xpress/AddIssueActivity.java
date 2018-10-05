package com.example.sanidhya.m_xpress;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
    private static final String TAG = "AddIssueActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
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
