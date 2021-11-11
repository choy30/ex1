package com.example.ex1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class FishAdapter extends FirebaseRecyclerAdapter <Fish, FishAdapter.fishViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FishAdapter(@NonNull FirebaseRecyclerOptions<Fish> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull fishViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Fish model) {
        holder.name.setText(model.getName());
        holder.breed.setText(model.getBreed());
        holder.age.setText(model.getAge());
        holder.weight.setText(model.getWeight());

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.fish_edit))
                        .setExpanded(true, 900)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txt_name);
                EditText breed = view.findViewById(R.id.txt_breed);
                EditText age = view.findViewById(R.id.txt_age);
                EditText weight = view.findViewById(R.id.txt_weight);

                Button btnupdate = view.findViewById(R.id.btn_update);

                name.setText(model.getName());
                breed.setText(model.getBreed());
                age.setText(model.getAge());
                weight.setText(model.getWeight());

                dialogPlus.show();

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("breed", breed.getText().toString());
                        map.put("age", age.getText().toString());
                        map.put("weight", weight.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("fishes")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public fishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_item, parent, false);
        return new fishViewHolder(view);
    }

    class fishViewHolder extends RecyclerView.ViewHolder {

        TextView name, breed, age, weight;
        Button btnedit, btndelete;

        public fishViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txt_name);
            breed = (TextView) itemView.findViewById(R.id.txt_breed);
            age = (TextView) itemView.findViewById(R.id.txt_age);
            weight = (TextView) itemView.findViewById(R.id.txt_weight);


            btnedit = (Button) itemView.findViewById(R.id.btn_edit);
            btndelete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }
}
