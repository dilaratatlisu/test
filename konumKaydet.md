package com.dilaratatlisu.javamaps.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.model.Locations;
import com.dilaratatlisu.javamaps.roomdb.PlaceDao;
import com.dilaratatlisu.javamaps.roomdb.PlaceDataBase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.dilaratatlisu.javamaps.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;
    PlaceDataBase db;
    PlaceDao placeDao;
    boolean info;
    Double selectedLatitude;
    Double selectedlongitude;
    Locations selectedLocations;
    LatLng latLng =null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        registerLauncher();

        sharedPreferences = MapsActivity.this.getSharedPreferences("com.dilaratatlisu.javamaps", MODE_PRIVATE);
        info = false;

        db = Room.databaseBuilder(getApplicationContext(), PlaceDataBase.class, "Places").build();
        placeDao = db.placeDao();

        selectedLatitude = 0.0;
        selectedlongitude = 0.0;

        binding.saveButton.setEnabled(false);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        Intent intent = getIntent();
        String intentInfo = intent.getStringExtra("info");

        //Sqlite data && intent data
        if ("new".equals(intentInfo)) {

            binding.saveButton.setVisibility(View.VISIBLE);
            binding.deleteButton.setVisibility(View.GONE);

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    info = sharedPreferences.getBoolean("info", false);
                    if (!info) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                        sharedPreferences.edit().putBoolean("info", true).apply();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
            };


            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(binding.getRoot(), "Permission needed for maps", Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // request permission
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

                        }
                    }).show();
                } else {
                    //request permission
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }

            } else {
                //gomaps
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    LatLng userLastLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation, 15));
                }

                mMap.setMyLocationEnabled(true);

            }


        } else {

            mMap.clear();

            selectedLocations = (Locations) intent.getSerializableExtra("place");

            if (selectedLocations !=null){
                latLng = new LatLng(selectedLocations.latitude, selectedLocations.longitude);
                mMap.addMarker(new MarkerOptions().position(latLng).title(selectedLocations.name));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                binding.placeName.setText(selectedLocations.name);
                binding.saveButton.setVisibility(View.GONE);
                binding.deleteButton.setVisibility(View.VISIBLE);
            }



        }

    }

    private void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        //permission granted
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);

                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastLocation!= null){
                            LatLng userLastLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation, 15));
                        }
                    }

                } else {
                    //permission denied
                    Toast.makeText(MapsActivity.this, "Permission needed for maps!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));

        selectedLatitude = latLng.latitude;
        selectedlongitude = latLng.longitude;

        binding.saveButton.setEnabled(true);
    }

    public void save(View view){
        Locations locations = new Locations(binding.placeName.getText().toString(), selectedLatitude, selectedlongitude);

        compositeDisposable.add(placeDao.insert(locations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MapsActivity.this :: handleResponse  ));

    }

    private void handleResponse(){
        Intent intent = new Intent(MapsActivity.this, SavedLocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete(View view){

        if (selectedLocations != null){
            compositeDisposable.add(placeDao.delete(selectedLocations)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(MapsActivity.this :: handleResponse));
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable!=null){
            compositeDisposable.clear();
        }

    }
}


