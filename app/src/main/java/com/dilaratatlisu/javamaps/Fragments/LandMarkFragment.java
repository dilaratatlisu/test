package com.dilaratatlisu.javamaps.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.adapter.CategoryAdapter;
import com.dilaratatlisu.javamaps.adapter.PopularAdapter;
import com.dilaratatlisu.javamaps.model.Category;
import com.dilaratatlisu.javamaps.model.Places;
import com.dilaratatlisu.javamaps.model.User;
import com.dilaratatlisu.javamaps.view.FollowersActivity;
import com.dilaratatlisu.javamaps.view.MapsActivity;
import com.dilaratatlisu.javamaps.view.SavedLocationActivity;
import com.dilaratatlisu.javamaps.view.SeeAllActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class LandMarkFragment extends Fragment  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    SocialAutoCompleteTextView searchView;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    private RecyclerView.Adapter adapterPopular, adapterCategory;
    private RecyclerView recyclerViewPopular, recyclerViewCategory;
    View view;
    ArrayList<Places> popularItems;
    TextView seeAllButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_land_mark, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbarLandmark);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        seeAllButton = view.findViewById(R.id.seeallbutton);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        searchView = view.findViewById(R.id.searchViewLandmark);

        arrayList = new ArrayList<>();
        arrayList.add("Monday");
        arrayList.add("Tuesday");
        arrayList.add("Wednesday");
        arrayList.add("Thursday");
        arrayList.add("Friday");
        arrayList.add("Saturday");
        arrayList.add("Sunday");

        adapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        searchView.setAdapter(adapter);
        //listView.setAdapter(adapter);

        connectRecyclerView();

        seeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeeAllActivity.class);
                startActivity(intent);
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });

        return view;

    }


    public void connectRecyclerView() {

        popularItems = new ArrayList<>();
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Great Wall of China","China","Great Wall of China, extensive bulwark erected in ancient China, one of the largest building-construction projects ever undertaken. The Great Wall actually consists of numerous walls—many of them parallel to each other—built over some two millennia across northern " +
                "China and southern Mongolia. The most extensive and best-preserved version of the wall dates from the Ming dynasty (1368–1644) and runs for some 5,500 miles (8,850 km) east to west from Mount Hu near Dandong, southeastern Liaoning province, to Jiayu Pass west of Jiuquan, northwestern Gansu province.","great_wall_of_china"));
        popularItems.add(new Places("Maldives","Indian Ocean","Maldives, independent island country in the north-central Indian Ocean. It consists of a chain of about 1,200 small coral islands and sandbanks (some 200 of which are inhabited), grouped in clusters, or atolls. The islands extend more than 510 miles (820 km) from north to " +
                "south and 80 miles (130 km) from east to west. The northernmost atoll is about 370 miles (600 km) south-southwest of the Indian mainland, and the central area, including the capital island of Male (Male’), is about 400 miles (645 km) southwest of Sri Lanka.","maldivler_"));
        popularItems.add(new Places("The Taj Mahal","India","Taj Mahal, mausoleum complex in Agra, western Uttar Pradesh state, northern India. The Taj Mahal was built by the Mughal emperor Shah Jahān (reigned 1628–58) to immortalize his wife Mumtaz Mahal (“Chosen One of the Palace”), who died in childbirth in 1631, having been the emperor’s" +
                " inseparable companion since their marriage in 1612. India’s most famous and widely recognized building, it is situated in the eastern part of the city on the southern (right) bank of the Yamuna (Jumna) River. Agra Fort (Red Fort), also on the right bank of the Yamuna, is about 1 mile (1.6 km) west of the Taj Mahal.","the_taj_mahal"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popularItems.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));

        recyclerViewPopular = view.findViewById(R.id.popularRecyclerView);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterPopular = new PopularAdapter(popularItems);
        recyclerViewPopular.setAdapter(adapterPopular);

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Beaches","beach_3719925"));
        categoryList.add(new Category("Historical Places","historic_site"));
        categoryList.add(new Category("Museums","museum_10607452"));
        categoryList.add(new Category("Mountains","mountain"));
        categoryList.add(new Category("Forests","trees"));
        categoryList.add(new Category("Caves","cave_7105096"));
        categoryList.add(new Category("Camps","camping_6341788"));

        recyclerViewCategory = view.findViewById(R.id.categoryRecyclerView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategory = new CategoryAdapter(categoryList);
        recyclerViewCategory.setAdapter(adapterCategory);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_location) {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.saved_locations) {
            Intent intent2 = new Intent(getActivity(), SavedLocationActivity.class);
            startActivity(intent2);

        }
        if (item.getItemId() == R.id.saved_places) {

        }


        return false;
    }
}