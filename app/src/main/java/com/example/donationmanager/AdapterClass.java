package com.example.donationmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterClass extends  RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    private DatabaseReference databaseReference;
    ImageButton deleteButton;
    ArrayList<Booking> list;
    public AdapterClass(ArrayList<Booking> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_layout,viewGroup, false);
        deleteButton = (ImageButton) view.findViewById(R.id.delete_btn);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final int x = i;
        myViewHolder.charityname_tv.setText(list.get(i).getCharityName());
        myViewHolder.description_tv.setText("Description: " + list.get(i).getDescription());
        myViewHolder.furnitureType_tv.setText("Furniture Type: " + list.get(i).getFurnitureType());
        myViewHolder.deliveryType_tv.setText("Delivery Type: " + list.get(i).getDonationType());
        myViewHolder.timeSlot_tv.setText("Time: " + list.get(i).getTimeSlot());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Delete button triggered" + list.get(myViewHolder.getAdapterPosition()).getBookingKey());
                final String bookingKey = list.get(myViewHolder.getAdapterPosition()).getBookingKey();
                databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");
                databaseReference.child(bookingKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        

                    }
                });
            }

        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView charityname_tv, description_tv, furnitureType_tv, timeSlot_tv, deliveryType_tv, timeStamp_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");
            charityname_tv = itemView.findViewById(R.id.charityname_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
            furnitureType_tv = itemView.findViewById(R.id.furnitureType);
            timeSlot_tv = itemView.findViewById(R.id.timeSlot);
            deliveryType_tv = itemView.findViewById(R.id.donationType);
            timeStamp_tv = itemView.findViewById(R.id.timeStamp);


        }
    }

}
