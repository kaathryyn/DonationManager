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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;


public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText editCharityName, editFirstName, editLastName, editAddress, editCity, editPostcode, editState, editPhoneNumber;
    private int openHour, closeHour;
    private CheckBox mon, tue, wed, thu, fri, sat, sun;
    private Button buttonSave;
    private String accountType;
    private TextView tvOpenHours, tvCloseHours, tvOpenDays;
    //initialise db reference
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    int accountPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);



        //initialise user information fields
        tvOpenHours = (TextView) v.findViewById(R.id.tvOpenHours);
        tvCloseHours = (TextView) v.findViewById(R.id.tvCloseHours);
        tvOpenDays = (TextView) v.findViewById(R.id.tvDays);

        buttonSave = (Button) v.findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(this);
        editFirstName = (EditText) v.findViewById(R.id.editFirstName);
        editLastName = (EditText) v.findViewById(R.id.editLastName);
        editAddress = (EditText) v.findViewById(R.id.editAddress);
        editCharityName = (EditText) v.findViewById(R.id.editCharityName);

        //Initialise and setup account selector spinner
        Spinner  spinner1 = (Spinner) v.findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(v.getContext(),R.array.accountType,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        //initialise days checkboxes

        mon = (CheckBox) v.findViewById(R.id.checkbox_monday);
        tue = (CheckBox) v.findViewById(R.id.checkbox_tuesday);
        wed = (CheckBox) v.findViewById(R.id.checkbox_wednesday);
        thu = (CheckBox) v.findViewById(R.id.checkbox_thursday);
        fri = (CheckBox) v.findViewById(R.id.checkbox_friday);
        sat = (CheckBox) v.findViewById(R.id.checkbox_saturday);
        sun = (CheckBox) v.findViewById(R.id.checkbox_sunday);


        accountType = spinner1.getSelectedItem().toString();


        //initialise and setup state spinner
        Spinner  spinner2 = (Spinner) v.findViewById(R.id.stateTypeSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(v.getContext(),R.array.states,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        //initialise opening hours spinner

        Spinner  spinner3 = (Spinner) v.findViewById(R.id.openHourSpinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(v.getContext(),R.array.hours,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        //initialise closing hours spinner

        Spinner  spinner4 = (Spinner) v.findViewById(R.id.closeHourSpinner);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(v.getContext(),R.array.hours,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter3);
        spinner4.setOnItemSelectedListener(this);





        //hide or show fields based on account type
        accountPosition = spinner1.getSelectedItemPosition() +1;
        if(spinner1.getSelectedItemPosition() + 1 == 1) {
            editCharityName.setVisibility(View.GONE);
            tvOpenDays.setVisibility(View.GONE);
            tvCloseHours.setVisibility(View.GONE);
            tvOpenHours.setVisibility(View.GONE);
            spinner4.setVisibility(View.GONE);
            spinner3.setVisibility(View.GONE);
            mon.setVisibility(View.INVISIBLE);
            tue.setVisibility(View.INVISIBLE);
            wed.setVisibility(View.INVISIBLE);
            thu.setVisibility(View.INVISIBLE);
            fri.setVisibility(View.INVISIBLE);
            sat.setVisibility(View.INVISIBLE);
            sun.setVisibility(View.INVISIBLE);





        }
        else {
            editFirstName.setVisibility(v.GONE);
            editLastName.setVisibility(v.GONE);
        }



        return v;




    }



    private void saveUserInfo() {
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        DonorInformation donorInformation = new DonorInformation(firstName, lastName, address, accountType, uId);


        databaseReference.child("donors").push().setValue(donorInformation);
        Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        if(parent.getSelectedItemPosition() == 0) {
            editCharityName.setVisibility(View.GONE);
            editFirstName.setVisibility(View.VISIBLE);
            editLastName.setVisibility(View.VISIBLE);
        }

        else if (parent.getSelectedItemPosition() == 1) {
            editFirstName.setVisibility(View.GONE);
            editLastName.setVisibility(View.GONE);
            editCharityName.setVisibility(View.VISIBLE);
            tvOpenDays.setVisibility(View.VISIBLE);
            tvCloseHours.setVisibility(View.VISIBLE);
            tvOpenHours.setVisibility(View.VISIBLE);
            mon.setVisibility(getView().VISIBLE);
            tue.setVisibility(getView().VISIBLE);
            wed.setVisibility(getView().VISIBLE);
            thu.setVisibility(getView().VISIBLE);
            fri.setVisibility(getView().VISIBLE);
            sat.setVisibility(getView().VISIBLE);
            sun.setVisibility(getView().VISIBLE);


            //tue.setVisibility(View.VISIBLE);
            //wed.setVisibility(View.VISIBLE);
            //thu.setVisibility(View.VISIBLE);
            //fri.setVisibility(View.VISIBLE);
            //sat.setVisibility(View.VISIBLE);
            //sun.setVisibility(View.VISIBLE);
            //spinner4.setVisibility(View.GONE);
            //spinner3.setVisibility(View.GONE);




        }

        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        if(v == buttonSave) {
            saveUserInfo();
            
            Fragment fragment = null;
            fragment = new BookingFragment();
            replaceFragment(fragment);
            
            
        }



    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
