package com.example.donationmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private DrawerLayout drawer;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase, FirebaseDatabase1;
    String initialSetup = "null";
    Menu menu;
    MenuItem profile;

    //activity elements
    private TextView textViewUserEmail, textViewName;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialise firebase object
        firebaseAuth = FirebaseAuth.getInstance();

        //User ID of logged in user
        final String uid = firebaseAuth.getCurrentUser().getUid();
        System.out.println(uid);

        //reference childs info using User ID
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initialSetup = extras.getString("initialValue", "true");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        if(firebaseAuth != null){

            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());


            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        initialSetup = "true";
                        profile.setTitle("Manage Account");

                        if (dataSnapshot.child("accountType").getValue().toString().equals("Donor")) {
                            System.out.println(dataSnapshot.child("accountType").getValue().toString());
                            String fName = dataSnapshot.child("firstName").getValue().toString();
                            String lName = dataSnapshot.child("lastName").getValue().toString();
                            textViewName.setText(fName + " " + lName);
                        } else if (dataSnapshot.child("accountType").getValue().toString().equals("Charity")) {
                            System.out.println(dataSnapshot.child("accountType").getValue().toString());
                            String charityName = dataSnapshot.child("charityName").getValue().toString();
                            textViewName.setText(charityName);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //check if initial setup has been performed


        // set name in menu after initial setup
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check if user has made a profile
                if (dataSnapshot.exists()) {
                    profile.setTitle("Manage Account");
                    if (dataSnapshot.child("accountType").getValue() == "Donor") {
                        String firstName = dataSnapshot.child("firstName").getValue().toString();
                        String lastName = dataSnapshot.child("lastName").getValue().toString();
                        textViewName.setText(firstName + " " + lastName);
                    } else if (dataSnapshot.child("accountType").getValue() == "Charity") {

                        textViewName.setText(dataSnapshot.child("charityName").getValue().toString());
                        System.out.println(dataSnapshot.child("charityName").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        checkUserLogin();

        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initialise view objects

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        profile = menu.findItem(R.id.nav_profile);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        textViewUserEmail = (TextView) headerView.findViewById(R.id.textViewUserEmail);
        textViewName = (TextView) headerView.findViewById(R.id.textViewName);
        buttonLogout = (Button) headerView.findViewById(R.id.buttonLogout);

        //display account email
        textViewUserEmail.setText(user.getEmail());

        //set default fragment to profile.
        if (savedInstanceState == null) {
            System.out.println("setting default fragment");
            if (initialSetup.equals("true")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManageProfileFragment()).commit();
                profile.setTitle("Manage Account");
            }
            else if (initialSetup.equals("null"))
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();



            navigationView.setCheckedItem(R.id.nav_profile);
        }

        //add click listener on logout button
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_profile:
                if (initialSetup.equals("false"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManageProfileFragment()).commit();
                break;

            case R.id.nav_booking:
                if (initialSetup.equals("true"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookingFragment()).commit();
                break;

            case R.id.nav_search:
                if (initialSetup.equals("true"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                break;

            case R.id.nav_search_donor:
                if (initialSetup.equals("true"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment_Donor()).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void checkUserLogin() {
        if (firebaseAuth.getCurrentUser() == null) {
            //close activity
            finish();
            //go back to login
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View view) {

        //if user logs out

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
