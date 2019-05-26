package com.example.donationmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageProfileFragment extends Fragment{

    private Button editProfilebtn;
    private EditText search_field;
    private RecyclerView results_list;
    private ImageView search_btn;
    private DatabaseReference bookingReference;
    private ArrayList<Booking> list;
    private String UID;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_profile, container, false);

            System.out.println("Checkpoint1: onCreate Start");
            bookingReference = FirebaseDatabase.getInstance().getReference("Bookings");
            search_field = v.findViewById(R.id.search_field);
            results_list = v.findViewById(R.id.results_list);
            editProfilebtn = v.findViewById(R.id.editProfilebtn);
            editProfilebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            search_btn = v.findViewById(R.id.search_btn);
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    search(search_field.getText().toString());

                }
            });

            UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            System.out.println("uid " + UID);

        list = new ArrayList<>();



        if(bookingReference != null){
            System.out.println("Checkpoint2: bookingref not null");
            bookingReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    System.out.println("Checkpoint3: ondatachange Start");

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        System.out.println("Checkpoint4: looping through bookings");

                        String donorID = ds.child("donorID").getValue().toString();
                        System.out.println("Checkpoint4.5: donorID " + donorID);
                        String charityID = ds.child("charityID").getValue().toString();

                        if (donorID.equals(UID) || charityID.equals(UID)) {
                            System.out.println("Checkpoint5: is uid = donorid yes");

                            list.add(ds.getValue(Booking.class));
                        }
                    }

                    AdapterClass adapterClass = new AdapterClass(list);
                    results_list.setAdapter(adapterClass);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        return v;
    }

    private void search(String str){
        ArrayList<Booking> myList = new ArrayList<>();
        for (Booking object : list){
            if (object.getCharityName().toLowerCase().contains(str.toLowerCase())
                    || object.getFurnitureType().toLowerCase().contains(str.toLowerCase())
                    || object.getDescription().toLowerCase().contains(str.toLowerCase()) ){
                myList.add(object);
            }
        }
        AdapterClass adapterClass = new AdapterClass(myList);
        results_list.setAdapter(adapterClass);
    }




}




