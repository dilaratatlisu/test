package com.dilaratatlisu.javamaps.view;



import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dilaratatlisu.javamaps.Fragments.LandMarkFragment;
import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.adapter.CategoryAdapter;
import com.dilaratatlisu.javamaps.adapter.PopularAdapter;
import com.dilaratatlisu.javamaps.databinding.ActivitySeeAllBinding;
import com.dilaratatlisu.javamaps.model.Category;
import com.dilaratatlisu.javamaps.model.Places;

import java.util.ArrayList;

public class SeeAllActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterpop, adaptercat;
    private RecyclerView recycpop, rcyccat;
    ArrayList<Places> popular;
    ArrayList<Category> category;
    private ActivitySeeAllBinding binding;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySeeAllBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        connectRecyclerViewSeeAll();
    }

    private void connectRecyclerViewSeeAll() {

        popular = LandMarkFragment.getPopularItems();


            recycpop = binding.popRec;
            recycpop.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            adapterpop = new PopularAdapter(popular);
            recycpop.setAdapter(adapterpop);


        category = LandMarkFragment.getCategoryList();


            rcyccat = binding.catRec;
            rcyccat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
            adaptercat = new CategoryAdapter(category);
            rcyccat.setAdapter(adaptercat);

    }


}