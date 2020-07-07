package com.example.moodtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    public static final Pattern EMAIL_ADDRESS = null;

    private String fullNameField;
    private String emailField;
    private String passwordField;
    private boolean acceptTC;
    private FirebaseAuth fAtuh;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        if (view.getId() == R.id.accept_terms_and_cond) {
            if (checked) {
                acceptTC = true;
            } else {
                acceptTC = false;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        final EditText fullName = findViewById( R.id.full_name );
        final EditText email = findViewById(R.id.email_field);
        final EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login_button);
        final Button loginAction = findViewById( R.id.login );

        fAtuh = FirebaseAuth.getInstance();

        email.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //emailField = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        password.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // passField = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );



        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullNameField = fullName.getText().toString();
                emailField = email.getText().toString();
                passwordField = password.getText().toString();
                Pattern emailPattern = Pattern.compile( "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)" );

                if (emailField.isEmpty()) {
                    email.setError("Fill the input with a valid email address");
                } else if (!emailPattern.matcher( emailField ).matches()) {
                    email.setError( "Invalid email address" );
                } else if (fullNameField.isEmpty()) {
                    fullName.setError("Fill the input with a valid name");
                } else if (acceptTC == false) {
                    Toast toast = Toast.makeText( getApplicationContext(), "You need to accept T&C", Toast.LENGTH_SHORT );
                    toast.show();
                } else if (emailPattern.matcher( emailField ).matches() && acceptTC == true) {

                    Toast.makeText( getApplicationContext(), "SUCCESS!", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                }
                    AuthCredential credential = EmailAuthProvider.getCredential( emailField, passwordField );
                    Objects.requireNonNull( fAtuh.getCurrentUser() ).linkWithCredential( credential ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText( Register.this,  "Notes are synced.", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( Register.this,  "Try again.", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                        }
                    } );

            }
        } );

        loginAction.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), Login.class ) );
            }
        } );
    }
}
