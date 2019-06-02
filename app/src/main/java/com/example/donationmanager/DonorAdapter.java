package com.example.donationmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorAdapter extends  RecyclerView.Adapter<DonorAdapter.MyViewHolder> {

    ArrayList<DonorInformation> list;
    public DonorAdapter(ArrayList<DonorInformation> list){
        this.list = list;
    }

    @NonNull
    @Override
    public DonorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_layout_donor,viewGroup, false);

        return new DonorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorAdapter.MyViewHolder myViewHolder, int i) {
        //String days = "Days: ";

        myViewHolder.donorname_tv.setText(list.get(i).getFirstName());
        myViewHolder.city_tv.setText("City: " + list.get(i).getCity());
        myViewHolder.state_tv.setText("State: " + list.get(i).getState());
        myViewHolder.phone_tv.setText("Contact: " + list.get(i).getPhoneNumber());
        myViewHolder.address_tv.setText("Address: " + list.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView donorname_tv, city_tv, state_tv, phone_tv, address_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donorname_tv = itemView.findViewById(R.id.sdonorName_tv);
            city_tv = itemView.findViewById(R.id.scity_tv);
            state_tv = itemView.findViewById(R.id.sstate_tv);
            phone_tv = itemView.findViewById(R.id.sphone_tv);
            address_tv = itemView.findViewById(R.id.saddress_tv);

        }
    }
}
