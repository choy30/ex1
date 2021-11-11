package com.example.ex1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

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
    protected void onBindViewHolder(@NonNull fishViewHolder holder, int position, @NonNull Fish model) {
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

                dialogPlus.show();
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
