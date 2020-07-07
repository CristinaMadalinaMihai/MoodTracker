package com.example.moodtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.moodtracker.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

public class AddMood extends AppCompatActivity {

    private EditText todayMood;
    private FirebaseFirestore fireStore;
    FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_mood );

        fireStore = FirebaseFirestore.getInstance();
        todayMood = findViewById( R.id.message );

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        ImageButton surprised = findViewById( R.id.surprised );
        ImageButton confused = findViewById( R.id.confused );
        ImageButton optimistic = findViewById( R.id.optimistic );
        ImageButton bored = findViewById( R.id.bored );
        ImageButton happy = findViewById( R.id.happy );
        ImageButton angry = findViewById( R.id.angry );

        surprised.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedMessage = todayMood.getText() + "surprised ";
                todayMood.setText( updatedMessage );
            }
        } );

        confused.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedMessage = todayMood.getText() + "confused ";
                todayMood.setText( updatedMessage );
            }
        } );

        optimistic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedMessage = todayMood.getText() + "optimistic ";
                todayMood.setText( updatedMessage );
            }
        } );

        bored.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedMessage = todayMood.getText() + "bored ";
                todayMood.setText( updatedMessage );
            }
        } );

        happy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedMessage = todayMood.getText() + "happy ";
                todayMood.setText( updatedMessage );
            }
        } );

        angry.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedMessage = todayMood.getText() + "angry ";
                todayMood.setText( updatedMessage );
            }
        } );

        Button submitButton = findViewById( R.id.submit_mood );
        submitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = java.time.LocalDate.now() + ": " + todayMood.getText().toString();

                if (message.isEmpty()) {
                    Toast.makeText( AddMood.this, "You should kinda express yourself", Toast.LENGTH_SHORT ).show();
                    return;
                }

                // save mood
                DocumentReference docRef = fireStore.collection( "notes" ).document(user.getUid()).collection( "notes").document();
                Map<String, Object> mood = new HashMap<>();
                mood.put( "thoughts",  message);

                docRef.set( mood ).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText( AddMood.this, "Feelings registered", Toast.LENGTH_SHORT ).show();
                        onBackPressed();
                    }
                } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( AddMood.this, "Failed to store", Toast.LENGTH_SHORT ).show();
                        onBackPressed();
                    }
                } );
            }
        } );
    }
}
