package com.example.donationmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText search_field;
    private RecyclerView results_list;
    private ImageView search_btn;
    private DatabaseReference charityReference;
    private ArrayList<CharityInformation> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);


        charityReference = FirebaseDatabase.getInstance().getReference("users");
        list = new ArrayList<>();
        search_field = v.findViewById(R.id.search_field);
        results_list = v.findViewById(R.id.results_charity);
        search_btn = v.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search(search_field.getText().toString());

            }
        });


        charityReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String accountType = ds.child("accountType").getValue().toString();

                    if (accountType.equals("Charity")) {
                        list.add(ds.getValue(CharityInformation.class));

                    }
                }

                CharityAdapter charityAdapter = new CharityAdapter(list);
                results_list.setAdapter(charityAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        return v;
    }

    private void search(String str){
        ArrayList<CharityInformation> myList = new ArrayList<>();
        for (CharityInformation object : list){
            if (object.getCharityName().toLowerCase().contains(str.toLowerCase())
                    || object.getCity().toLowerCase().contains(str.toLowerCase())
                    || object.getPhoneNumber().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        CharityAdapter charityAdapter = new CharityAdapter(myList);
        results_list.setAdapter(charityAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
