package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name, breed, age, weight;
    Button btnadd;

    RecyclerView recyclerView;
    FishAdapter fishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.txt_name);
        breed = (EditText)findViewById(R.id.txt_breed);
        age = (EditText)findViewById(R.id.txt_age);
        weight = (EditText)findViewById(R.id.txt_weight);

        btnadd = (Button)findViewById(R.id.btn_add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAdd();
            }
        });

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

    private void dataAdd() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("breed", breed.getText().toString());
        map.put("age", age.getText().toString());
        map.put("weight", weight.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("fishes").push()
                .setValue(map);
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