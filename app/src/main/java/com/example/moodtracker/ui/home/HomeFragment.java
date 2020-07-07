package com.example.moodtracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.moodtracker.R;
import com.example.moodtracker.ui.model.Adapter;
import com.example.moodtracker.ui.model.Mood;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public RecyclerView recyclerView;
    public Adapter adapter;


    private List<Mood> moodList = new ArrayList<>(  );
    public List<String> content = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_home, container, false );

        recyclerView = root.findViewById( R.id.items );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false );
        recyclerView.setLayoutManager( linearLayoutManager );


        content.add( "My first mood:)" );
        content.add( "My second mood:(" );

        getNotes();

        adapter = new Adapter( content );
        recyclerView.setAdapter( adapter );


        return root;
    }
    public void getNotes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference moodsCollection = db.collection( "notes" );

        Query moodsQuery = moodsCollection.whereEqualTo( "thoughts", FirebaseAuth.getInstance().getCurrentUser().getUid() );
        moodsQuery.get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() ) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Mood mood = documentSnapshot.toObject( Mood.class );
                        moodList.add( mood );
                    }
                    for (Mood moodd : moodList) {
                        content.add( moodd.getThoughts() );
                        System.out.println("/////////////////////////////////////////" + moodd.getThoughts());
                    }
                } else {
                    Snackbar.make( getView(), "Query failed.", Snackbar.LENGTH_LONG )
                            .setAction( "Action", null ).show();
                }
            }
        } );
    }

}
