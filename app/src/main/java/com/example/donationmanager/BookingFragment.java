package com.example.donationmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BookingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner charitySpinner, donationTypeSpinner, furnitureTypeSpinner, timeSlotSpinner, daySlotSpinner;
    private EditText descriptionEdittext;
    private Button btnSubmit;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    Boolean[] days = new Boolean[]{false, false, false, false, false, false, false};
    ArrayAdapter<String> charityAdapter, timeSlotAdapter;

    Calendar lastSelectedCalendar = null;
    CalendarView calendarView;

    int count;
    String uId;
    String selectedCharity;
    String selectedCharityID;
    long bookingTimeStamp;
    int charityOpen;
    int charityClose;
    String charityOpenString;
    String charityCloseString;
    Boolean clash;
    Boolean dateSet = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_booking, container, false);


        calendarView = (CalendarView) v.findViewById(R.id.calenderView);
        lastSelectedCalendar = Calendar.getInstance();
        calendarView.setMinDate(lastSelectedCalendar.getTimeInMillis() - 1000);


        //setup and initilaise firebase auth and firebasedb
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uId = user.getUid();
        System.out.println("uid is " + uId);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        //initialise charity spinner values
        charitySpinner = v.findViewById(R.id.charitySpinner);
        charityAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item);
        charityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        charitySpinner.setPrompt("Select Charity");
        charitySpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        charityAdapter,
                        R.layout.contact_spinner_row_nothing_selected, getContext()));

        //query db for all available charity names and populate chairtyspinner with data
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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
        ArrayAdapter<CharSequence> adapterDonationType = ArrayAdapter.createFromResource(v.getContext(), R.array.donationType, android.R.layout.simple_spinner_item);
        adapterDonationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationTypeSpinner.setAdapter(adapterDonationType);
        donationTypeSpinner.setOnItemSelectedListener(this);


        furnitureTypeSpinner = v.findViewById(R.id.furnitureTypeSpinner);
        ArrayAdapter<CharSequence> adapterFurnitureType = ArrayAdapter.createFromResource(v.getContext(), R.array.furnitureType, android.R.layout.simple_spinner_item);
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

        System.out.println("selected charity id is " + selectedCharityID);
        System.out.println("uid is " + uId);
        //creates new booking object
        Booking booking = new Booking(
                selectedCharityID,
                charitySpinner.getSelectedItem().toString(),
                uId,
                descriptionEdittext.getText().toString(),
                donationTypeSpinner.getSelectedItem().toString(),
                furnitureTypeSpinner.getSelectedItem().toString(),
                timeSlotSpinner.getSelectedItem().toString(),
                bookingTimeStamp
        );

        System.out.println("selected charity id is " + booking.getCharityID());
        System.out.println("uid is " + booking.getDonorID());

        addBooking(booking);
        Fragment fragment = null;
        fragment = new ManageProfileFragment();
        replaceFragment(fragment);



    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        if (position != 0) {
            switch (parent.getId()) {

                case R.id.charitySpinner:
                    //find chosen charity
                    selectedCharity = parent.getSelectedItem().toString();

                    //clear data array
                    Arrays.fill(days, false);

                    //initialise spinner


                    //find available days

                    firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                String accountType = ds.child("accountType").getValue().toString();

                                if (accountType.equals("Charity")) {
                                    String dscharityName = ds.child("charityName").getValue().toString();

                                    if (dscharityName.equals(selectedCharity)) {
                                        //assigning charity id
                                        if(ds.getKey() != null)
                                            selectedCharityID = ds.getKey();
                                        System.out.println("charity id is " + selectedCharityID);
                                        charityOpen = Integer.parseInt(ds.child("openingHour").getValue().toString());
                                        charityOpenString = ds.child("openingHour").getValue().toString();
                                        charityClose = Integer.parseInt(ds.child("closingHour").getValue().toString());
                                        charityCloseString = ds.child("closingHour").getValue().toString();


                                        //find available days

                                        if (ds.child("mondayOpen").getValue().toString().equals("true")) {
                                            days[0] = true;
                                        }

                                        if (ds.child("tuesdayOpen").getValue().toString().equals("true")) {
                                            days[1] = true;
                                        }

                                        if (ds.child("wednesdayOpen").getValue().toString().equals("true")) {
                                            days[2] = true;
                                        }

                                        if (ds.child("thursdayOpen").getValue().toString().equals("true")) {
                                            days[3] = true;
                                        }

                                        if (ds.child("fridayOpen").getValue().toString().equals("true")) {
                                            days[4] = true;
                                        }

                                        if (ds.child("saturdayOpen").getValue().toString().equals("true")) {
                                            days[5] = true;
                                        }

                                        if (ds.child("sundayOpen").getValue().toString().equals("true")) {
                                            days[6] = true;
                                        }

                                    }

                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                            Calendar checkCalender = Calendar.getInstance();
                            checkCalender.set(year, month, dayOfMonth);
                            if (checkCalender.equals(lastSelectedCalendar)) {

                                return;
                            }

                            if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && !days[0].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) && !days[1].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) && !days[2].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) && !days[3].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && !days[4].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !days[5].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else if ((checkCalender.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) && !days[6].booleanValue()) {
                                calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                            } else {
                                lastSelectedCalendar = checkCalender;
                                checkCalender.set(Calendar.HOUR_OF_DAY, 0);
                                checkCalender.set(Calendar.MINUTE, 0);
                                checkCalender.set(Calendar.SECOND, 0);
                                checkCalender.set(Calendar.MILLISECOND, 0);
                                bookingTimeStamp = checkCalender.getTimeInMillis();
                                dateSet = true;
                                System.out.println("date set " + dateSet);


                                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        timeSlotAdapter.clear();

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                            String accountType = ds.child("accountType").getValue().toString();

                                            //if current entry has the same name as the selected charityspinner save open and close hours as int
                                            if (accountType.equals("Charity") && selectedCharityID.equals(ds.getKey())) {


                                                timeSlotAdapter.clear();
                                                DatabaseReference bookingDB = FirebaseDatabase.getInstance().getReference().child("Bookings");
                                                bookingDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        timeSlotAdapter.clear();
                                                        for (count = charityOpen; count < charityClose; count += 100) {
                                                            clash = false;
                                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                if (ds.child("charityName").getValue().toString().equals(selectedCharity)) {

                                                                    if (ds.child("timeStamp").getValue().toString().equals(Long.toString(bookingTimeStamp))) {

                                                                        if (count == Integer.parseInt(ds.child("timeSlot").getValue().toString())) {
                                                                            clash = true;
                                                                        }

                                                                    }
                                                                }

                                                            }
                                                            if (!clash) {
                                                                String padded = String.format("%04d", count);
                                                                timeSlotAdapter.add(padded);
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });


                                                //}

                                            }
                                        }
                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                });

                            }


                        }


                    });


                    firebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            timeSlotAdapter.clear();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                String accountType = ds.child("accountType").getValue().toString();

                                //if current entry has the same name as the selected charityspinner save open and close hours as int
                                if (accountType.equals("Charity") && selectedCharity.equals(ds.child("charityName").getValue().toString())) {


                                    //add hours to spinner
                                    for (count = charityOpen; count < charityClose; count += 100) {
                                        clash = false;

                                        DatabaseReference bookingDB = FirebaseDatabase.getInstance().getReference().child("Bookings");
                                        bookingDB.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                    if (ds.child("charityName").getValue().toString().equals(selectedCharity)) {

                                                        if (ds.child("timeStamp").getValue().toString().equals(bookingTimeStamp)) {

                                                            if (ds.child("timeSlot").getValue().toString().equals(Integer.toString(count))) {
                                                                clash = true;
                                                            }


                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        if (!clash && dateSet) {
                                            String padded = String.format("%04d", count);
                                            timeSlotAdapter.add(padded);
                                        }

                                    }

                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });


                    break;

                case R.id.donationTypeSpinner:
                    if (position == 0) {

                    }
                    break;


            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
