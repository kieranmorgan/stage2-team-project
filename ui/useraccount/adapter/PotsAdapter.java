package com.example.teamproject.ui.useraccount.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.Pot;
import com.example.teamproject.ui.useraccount.AddPotClickListener;
import com.example.teamproject.ui.useraccount.DeletePotClickListener;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Adds the user pot information to cardviews.
 */

public class PotsAdapter extends RecyclerView.Adapter<PotsAdapter.MyViewHolder> {

    private ArrayList<Pot> potsList;
    private DeletePotClickListener<Pot> deletePotClickListener;
    private AddPotClickListener<Pot> addPotClickListener;

    public PotsAdapter(ArrayList<Pot> pots) {
        this.potsList = pots;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView potName, potAmount, potDescription;
        public Button add, delete;

        public MyViewHolder(final View view) {
            super(view);
            potName = view.findViewById(R.id.pot_name);
            potAmount = view.findViewById(R.id.pot_amount);
            potDescription = view.findViewById(R.id.pot_description);
            add = view.findViewById(R.id.add_pot);
            delete = view.findViewById(R.id.delete_pot);
        }
    }

    @Override
    public PotsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pot_layout,
                parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PotsAdapter.MyViewHolder holder, int position) {
        final Pot pot = potsList.get(position);

        holder.potName.setText(pot.getPotName());
        holder.potAmount.setText("£"+pot.getAmount());
        holder.potDescription.setText(pot.getPotDescription());

       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deletePotClickListener.onItemClick(pot);
           }
       });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPotClickListener.onItemClick(pot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return potsList.size();
    }

    public void setOnDeleteClickListener(DeletePotClickListener<Pot> deletePotClickListener) {
        this.deletePotClickListener = deletePotClickListener;
    }

    public void setOnAddClickListener(AddPotClickListener<Pot> addPotClickListener) {
        this.addPotClickListener = addPotClickListener;
    }
}


