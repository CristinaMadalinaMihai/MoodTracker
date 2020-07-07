package com.example.moodtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        fAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                // check if user is logged in
                if (fAuth.getCurrentUser() != null) {
                    startActivity( new Intent( getApplicationContext(), MainActivity.class) );
                    finish();
                } else { // create new anonymous account
                    fAuth.signInAnonymously().addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText( SplashActivity.this, "Logged in with temp acc", Toast.LENGTH_SHORT ).show();
                            startActivity( new Intent( getApplicationContext(), MainActivity.class) );
                            finish();
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( SplashActivity.this, "Error", Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }

            }
        }, SPLASH_TIME_OUT );
    }
}
