package com.example.donationmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClass extends  RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    ArrayList<Booking> list;
    public AdapterClass(ArrayList<Booking> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_layout,viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.charityname_tv.setText(list.get(i).getCharityName());
        myViewHolder.description_tv.setText(list.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView charityname_tv, description_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            charityname_tv = itemView.findViewById(R.id.charityname_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
        }
    }

}
