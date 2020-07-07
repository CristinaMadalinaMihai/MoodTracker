package com.example.moodtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    FirebaseAuth fAtuh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        email = findViewById( R.id.email_log );
        password = findViewById( R.id.password_log );
        login = findViewById( R.id.login_button );

        fAtuh = FirebaseAuth.getInstance();

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();

                Pattern emailPattern = Pattern.compile( "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)" );

                if (mEmail.isEmpty()) {
                    email.setError("Fill the input with a valid email address");
                } else if (!emailPattern.matcher( mEmail ).matches()) {
                    email.setError( "Invalid email address" );
                }

                    fAtuh.signInWithEmailAndPassword( mEmail, mPassword ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText( Login.this,  "Successful log in", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                            finish();

                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( Login.this,  "Failed to log in", Toast.LENGTH_SHORT).show();

                        }
                    } );

            }
        } );
    }
}
