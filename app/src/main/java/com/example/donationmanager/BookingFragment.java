package com.example.donationmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingFragment extends Fragment implements View.OnClickListener {

    private EditText charityEdittext, descriptionEdittext;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_booking, container, false);


        charityEdittext = v.findViewById(R.id.charityEdittext);
        descriptionEdittext = v.findViewById(R.id.descriptionEdittext);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


        return v;

    }


    //when submit is clicked
    @Override
    public void onClick(View view) {

        //creates new booking object
        Booking booking = new Booking(charityEdittext.getText().toString(), descriptionEdittext.getText().toString());
        addBooking(booking);

    }


    //adds booking to firebase
    private void addBooking(Booking booking) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Bookings").push();

        // get post unique ID and upadte post key
        String key = myRef.getKey();
        booking.setBookingKey(key);


        // add post data to firebase database

        myRef.setValue(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //add a "Booking was successfull" message popup
            }
        });





    }
}
