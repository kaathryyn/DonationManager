package com.example.donationmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CharityAdapter extends  RecyclerView.Adapter<CharityAdapter.MyViewHolder> {

    ArrayList<CharityInformation> list;
    public CharityAdapter(ArrayList<CharityInformation> list){
        this.list = list;
    }

    @NonNull
    @Override
    public CharityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_layout_charity,viewGroup, false);

        return new CharityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharityAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.charityname_tv.setText(list.get(i).getCharityName());
        myViewHolder.city_tv.setText("City: " + list.get(i).getCity());
        myViewHolder.phone_tv.setText("Contact: " + list.get(i).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView charityname_tv, city_tv, phone_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            charityname_tv = itemView.findViewById(R.id.charityname_tv);
            city_tv = itemView.findViewById(R.id.city_tv);
            phone_tv = itemView.findViewById(R.id.phone_tv);
        }
    }
}
