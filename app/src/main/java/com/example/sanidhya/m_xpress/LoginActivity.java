package com.example.sanidhya.m_xpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanidhya.m_xpress.Adapters.FeedListAdapter;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final String TAG = "LoginActivity";
    FirebaseAuth.AuthStateListener authStateListener;
    SharedPreferences.Editor editor;
    String user_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e(TAG, "onCreate: in");
        authenticate(new View(this));
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(LoginActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this, "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                editor = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(Constants.USER_EMAIL_PREF, firebaseAuth.getCurrentUser().getEmail());
                editor.putString(Constants.USERNAME_PREF, firebaseAuth.getCurrentUser().getDisplayName());
                editor.putString(Constants.PIC_URI_PREF, String.valueOf(firebaseAuth.getCurrentUser().getPhotoUrl()));
                firebaseAuth.getCurrentUser().getIdToken(true)
                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                if (task.isSuccessful()) {
                                    String idToken = task.getResult().getToken();
                                    user_token = idToken;
                                    editor.putString(Constants.UID_PREF, idToken);
                                } else {
                                    // Handle error -> task.getException();
                                }
                            }
                        });

                editor.apply();
                send_uid();
            } else {
                if (response == null) {
                    Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet connection.\nPlease try again later...", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
        onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void authenticate(View v) {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                editor = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(Constants.USER_EMAIL_PREF, user.getEmail());
                editor.putString(Constants.USERNAME_PREF, user.getDisplayName());
                editor.putString(Constants.PIC_URI_PREF, String.valueOf(user.getPhotoUrl()));
                firebaseAuth.getCurrentUser().getIdToken(true)
                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                if (task.isSuccessful()) {
                                    String idToken = task.getResult().getToken();
                                    user_token = idToken;
                                    editor.putString(Constants.UID_PREF, idToken);
                                } else {
                                    // Handle error -> task.getException();
                                }
                            }
                        });
                editor.apply();
                send_uid();
                Log.e(TAG, "onActivityResult: pic url = " + user.getPhotoUrl());
                Log.e(TAG, "onActivityResult: name = " + user.getDisplayName());
                Log.e(TAG, "onActivityResult: email = " + user.getEmail());
            } else {
                AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder()
                        .build();
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Collections.singletonList(googleIdp))
                                .setLogo(R.drawable.common_google_signin_btn_icon_light_normal) //TODO Use Logo
//                                .setTheme(R.style.GreenTheme) // TODO Use custom theme
                                .setIsSmartLockEnabled(false, true)
                                .build(),
                        Constants.RC_SIGN_IN);
            }
            onBackPressed();
        };
    }
    void send_uid() {
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        String TokenID = sp.getString(Constants.UID_PREF, null);
        if(TokenID != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String URL = Constants.LOGIN_URL+"?user_id="+user_token;
            StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
                Log.e(TAG, "onResponse: " + response);
            }, error -> Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show());
            queue.add(sr);

        }

    }
}