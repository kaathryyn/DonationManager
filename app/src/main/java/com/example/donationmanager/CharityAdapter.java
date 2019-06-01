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
        String days = "Days: ";

        myViewHolder.charityname_tv.setText(list.get(i).getCharityName());
        myViewHolder.city_tv.setText("City: " + list.get(i).getCity());
        myViewHolder.state_tv.setText("State: " + list.get(i).getState());
        myViewHolder.hours_tv.setText("Hours: " + String.format("%04d", list.get(i).getOpeningHour()) + " - " + String.format("%04d", list.get(i).getClosingHour()));

        if (list.get(i).mondayOpen) days += "Mon/";
        if (list.get(i).tuesdayOpen) days += "Tue/";
        if (list.get(i).wednesdayOpen) days += "Wed/";
        if (list.get(i).thursdayOpen) days += "Thur/";
        if (list.get(i).fridayOpen) days += "Fri/";
        if (list.get(i).saturdayOpen) days += "Sat/";
        if (list.get(i).sundayOpen) days += "Sun/";

        myViewHolder.days_tv.setText(days.substring(0, days.length() - 1));
        myViewHolder.phone_tv.setText("Contact: " + list.get(i).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView charityname_tv, city_tv, state_tv, phone_tv, hours_tv, days_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            charityname_tv = itemView.findViewById(R.id.charityname_tv);
            city_tv = itemView.findViewById(R.id.city_tv);
            state_tv = itemView.findViewById(R.id.state_tv);
            phone_tv = itemView.findViewById(R.id.phone_tv);
            hours_tv = itemView.findViewById(R.id.hours_tv);
            days_tv = itemView.findViewById(R.id.days_tv);
        }
    }
}
