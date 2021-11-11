package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FishAdapter fishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.lst_fish);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //https://firebaseopensource.com/projects/firebase/firebaseui-android/database/readme
        FirebaseRecyclerOptions<Fish> options =
                new FirebaseRecyclerOptions.Builder<Fish>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("fishes"), Fish.class)
                        .build();

        fishAdapter = new FishAdapter(options);
        recyclerView.setAdapter(fishAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fishAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fishAdapter.stopListening();
    }
}