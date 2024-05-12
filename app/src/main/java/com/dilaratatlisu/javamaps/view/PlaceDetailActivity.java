package com.dilaratatlisu.javamaps.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.adapter.PopularAdapter;
import com.dilaratatlisu.javamaps.model.Places;

public class PlaceDetailActivity extends AppCompatActivity {

    private TextView location, description, title;
    private ImageView savePlace, backButton, placeImage;
    private Places popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewSave();
        setVariable();
    }

    private void setVariable() {

        popular = (Places) getIntent().getSerializableExtra("object");

        if (popular != null) {
            title.setText(popular.getTitle());
            location.setText(popular.getLocation());
            description.setText(popular.getDescription());
            int drawableResID = getResources().getIdentifier(popular.getImage(), "drawable", getPackageName());
            Glide.with(this)
                    .load(drawableResID)
                    .into(placeImage);
            backButton.setOnClickListener(v -> finish());
        }



    }

    private void viewSave() {

        title = findViewById(R.id.textView);
        location = findViewById(R.id.locationtxt);
        description = findViewById(R.id.descriptionPlace);
        backButton = findViewById(R.id.golandmarkback);
        placeImage = findViewById(R.id.placeImageDetail);


    }
}