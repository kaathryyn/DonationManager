package com.example.donationmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner charitySpinner, donationTypeSpinner, furnitureTypeSpinner, timeSlotSpinner, daySlotSpinner;
    private EditText descriptionEdittext;
    private Button btnSubmit;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    List<String> days = new ArrayList<>();
    List<String> charities = new ArrayList<>();
    ArrayAdapter<String> daySlotAdapter, charityAdapter, timeSlotAdapter;


    String selectedCharity;
    String selectedCharityID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_booking, container, false);





        //setup and initilaise firebase auth and firebasedb
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        charitySpinner = v.findViewById(R.id.charitySpinner);
        charityAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item);
        charityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        charitySpinner.setAdapter(charityAdapter);

        //query db for all available charity names and populate chairtyspinner with data
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String accountType = ds.child("accountType").getValue().toString();

                    if (accountType.equals("Charity")) {
                        String charityName = ds.child("charityName").getValue().toString();
                        charityAdapter.add(charityName);

                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        charitySpinner.setOnItemSelectedListener(this);


        donationTypeSpinner = v.findViewById(R.id.donationTypeSpinner);
        ArrayAdapter<CharSequence> adapterDonationType = ArrayAdapter.createFromResource(v.getContext(),R.array.donationType,android.R.layout.simple_spinner_item);
        adapterDonationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationTypeSpinner.setAdapter(adapterDonationType);
        donationTypeSpinner.setOnItemSelectedListener(this);

        daySlotSpinner = v.findViewById(R.id.daySlotSpinner);
        daySlotAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item);
        daySlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySlotSpinner.setAdapter(daySlotAdapter);
        daySlotSpinner.setOnItemSelectedListener(this);

        furnitureTypeSpinner = v.findViewById(R.id.furnitureTypeSpinner);
        ArrayAdapter<CharSequence> adapterFurnitureType = ArrayAdapter.createFromResource(v.getContext(),R.array.furnitureType,android.R.layout.simple_spinner_item);
        adapterFurnitureType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        furnitureTypeSpinner.setAdapter(adapterFurnitureType);
        furnitureTypeSpinner.setOnItemSelectedListener(this);



        timeSlotSpinner = v.findViewById(R.id.timeSlotSpinner);
        timeSlotAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item);
        timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlotSpinner.setAdapter(timeSlotAdapter);


        descriptionEdittext = v.findViewById(R.id.descriptionEdittext);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


        return v;

    }


    //when submit is clicked
    @Override
    public void onClick(View view) {

        //creates new booking object
        Booking booking = new Booking(
                charitySpinner.getSelectedItem().toString(),
                descriptionEdittext.getText().toString(),
                donationTypeSpinner.getSelectedItem().toString(),
                furnitureTypeSpinner.getSelectedItem().toString(),
                daySlotSpinner.getSelectedItem().toString(),
                timeSlotSpinner.getSelectedItem().toString()
        );

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //selecting charity loads apporpriate available date, selecting day loads
        // appropriate free time slots

        switch(parent.getId()) {

            case R.id.charitySpinner :
                //find chosen charity
                selectedCharity = parent.getSelectedItem().toString();
                System.out.println(selectedCharity);


                //clear data array
                daySlotAdapter.clear();

                //initialise spinner


                //find available days

                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {

                            String accountType = ds.child("accountType").getValue().toString();

                            if (accountType.equals("Charity")) {
                                String dscharityName = ds.child("charityName").getValue().toString();

                                if (dscharityName.equals(selectedCharity)) {
                                    //assigning charity id
                                    selectedCharityID = ds.getKey();

                                    //find available days

                                    if (ds.child("mondayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Monday");
                                    }

                                    if (ds.child("tuesdayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Tuesday");
                                    }

                                    if (ds.child("wednesdayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Wednesday");
                                    }

                                    if (ds.child("thursdayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Thursday");
                                    }

                                    if (ds.child("fridayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Friday");
                                    }

                                    if (ds.child("saturdayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Tuesday");
                                    }

                                    if (ds.child("sundayOpen").getValue().toString().equals("true")) {
                                        daySlotAdapter.add("Sunday");
                                    }

                                    System.out.println(daySlotAdapter);
                                }

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;

            case R.id.daySlotSpinner :

                break;

        }

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timeSlotAdapter.clear();

                String charityName = charitySpinner.getSelectedItem().toString();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String accountType = ds.child("accountType").getValue().toString();

                    //if current entry has the same name as the selected charityspinner save open and close hours as int
                    if (accountType.equals("Charity") && charityName.equals(ds.child("charityName").getValue().toString())) {
                        int charityOpen = Integer.parseInt(ds.child("openingHour").getValue().toString());
                        int charityClose = Integer.parseInt(ds.child("closingHour").getValue().toString());

                        //add hours to spinner
                        for (int i = charityOpen; i < charityClose; i+=100) {

                            String padded = String.format("%04d", i);
                            timeSlotAdapter.add(padded);
                        }

                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



            /*
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {

                        //System.out.println(ds.child(selectedCharity));



                        /*if (ds.child("mondayOpen").equals("true")) {
                            days.add("Monday");
                        }

                        if (ds.child("tuesdayOpen").equals("true")) {
                            days.add("Tuesday");
                        }

                        if (ds.child("wednesdayOpen").equals("true")) {
                            days.add("Wednesday");
                        }

                        if (ds.child("thursdayOpen").equals("true")) {
                            days.add("Thursday");
                        }

                        if (ds.child("fridayOpen").equals("true")) {
                            days.add("Friday");
                        }

                        if (ds.child("saturdayOpen").equals("true")) {
                            days.add("Tuesday");
                        }

                        if (ds.child("sundayOpen").equals("true")) {
                            days.add("Sunday");
                        }

                    }

                    System.out.println(days);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
