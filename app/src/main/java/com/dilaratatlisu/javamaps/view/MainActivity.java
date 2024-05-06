package com.dilaratatlisu.javamaps.view;

import static com.dilaratatlisu.javamaps.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.dilaratatlisu.javamaps.Fragments.HomeFragment;
import com.dilaratatlisu.javamaps.Fragments.LandMarkFragment;
import com.dilaratatlisu.javamaps.Fragments.ProfileFragment;
import com.dilaratatlisu.javamaps.Fragments.SearchFragment;
import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.databinding.ActivityMainBinding;
import com.dilaratatlisu.javamaps.model.Locations;
import com.dilaratatlisu.javamaps.model.Post;
import com.dilaratatlisu.javamaps.roomdb.PlaceDao;
import com.dilaratatlisu.javamaps.roomdb.PlaceDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.android.material.navigation.NavigationBarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity{


    private ActivityMainBinding binding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    PlaceDataBase db;
    PlaceDao placeDao;
    ArrayList<Locations> locations;
    ArrayList<Post> postArrayList;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private Fragment selectedFragment;
    private FragmentManager fragmentManager;
    NavigationBarView navigationBarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        locations = new ArrayList<>();


        db = Room.databaseBuilder(getApplicationContext(), PlaceDataBase.class, "Places").build();
        placeDao = db.placeDao();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        navigationBarView = findViewById(id.bottomNavigationView);
        FloatingActionButton fab = findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });



        navigationBarView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == id.home) {
                    selectedFragment = new HomeFragment();
                }
                else if (item.getItemId()== id.search){
                    selectedFragment = new SearchFragment();
                }
                else if (item.getItemId() == id.places){
                    selectedFragment = new LandMarkFragment();
                }
                else if (item.getItemId() == id.profile){
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(id.container, selectedFragment).commit();
                }
                return true;
            }
        });


        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String profileId = intent.getString("publisherId");

            getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
            navigationBarView.setSelectedItemId(id.profile);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container , new HomeFragment()).commit();
        }
    }



}