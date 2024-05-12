package com.dilaratatlisu.javamaps.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.adapter.CategoryAdapter;
import com.dilaratatlisu.javamaps.adapter.PopularAdapter;
import com.dilaratatlisu.javamaps.model.Category;
import com.dilaratatlisu.javamaps.model.Places;

import java.util.ArrayList;

public class SeeAllActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    private RecyclerView.Adapter adapterPopular, adapterCategory;
    private RecyclerView recyclerViewPopular, recyclerViewCategory;
    View view;
    ArrayList<Places> popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_all);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        connectRecyclerViewSeeAll();
    }

    private void connectRecyclerViewSeeAll() {

        popular = new ArrayList<>();
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Great Wall of China","China","Great Wall of China, extensive bulwark erected in ancient China, one of the largest building-construction projects ever undertaken. The Great Wall actually consists of numerous walls—many of them parallel to each other—built over some two millennia across northern " +
                "China and southern Mongolia. The most extensive and best-preserved version of the wall dates from the Ming dynasty (1368–1644) and runs for some 5,500 miles (8,850 km) east to west from Mount Hu near Dandong, southeastern Liaoning province, to Jiayu Pass west of Jiuquan, northwestern Gansu province.","great_wall_of_china"));
        popular.add(new Places("Maldives","Indian Ocean","Maldives, independent island country in the north-central Indian Ocean. It consists of a chain of about 1,200 small coral islands and sandbanks (some 200 of which are inhabited), grouped in clusters, or atolls. The islands extend more than 510 miles (820 km) from north to " +
                "south and 80 miles (130 km) from east to west. The northernmost atoll is about 370 miles (600 km) south-southwest of the Indian mainland, and the central area, including the capital island of Male (Male’), is about 400 miles (645 km) southwest of Sri Lanka.","maldivler_"));
        popular.add(new Places("The Taj Mahal","India","Taj Mahal, mausoleum complex in Agra, western Uttar Pradesh state, northern India. The Taj Mahal was built by the Mughal emperor Shah Jahān (reigned 1628–58) to immortalize his wife Mumtaz Mahal (“Chosen One of the Palace”), who died in childbirth in 1631, having been the emperor’s" +
                " inseparable companion since their marriage in 1612. India’s most famous and widely recognized building, it is situated in the eastern part of the city on the southern (right) bank of the Yamuna (Jumna) River. Agra Fort (Red Fort), also on the right bank of the Yamuna, is about 1 mile (1.6 km) west of the Taj Mahal.","the_taj_mahal"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));
        popular.add(new Places("Colosseum","Rome","Colosseum, giant amphitheater built in Rome under the Flavian emperors. Construction of the Colosseum was begun sometime between 70 and 72 CE during the reign of Vespasian. " +
                "It is located just east of the Palatine Hill, on the grounds of what was Nero’s Golden House. The artificial lake that was the centerpiece of that palace complex was drained, and the Colosseum was sited there, a decision that " +
                "was as much symbolic as it was practical. Vespasian, whose path to the throne had relatively humble beginnings, chose to replace the tyrannical emperor’s private lake with a public amphitheater that could host tens of thousands of Romans.","colosseum"));

        if (recyclerViewPopular!=null){
            recyclerViewPopular = view.findViewById(R.id.popRec);
            recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            adapterPopular = new PopularAdapter(popular);
            recyclerViewPopular.setAdapter(adapterPopular);

        }



        ArrayList<Category> category = new ArrayList<>();
        category.add(new Category("Beaches","beach_3719925"));
        category.add(new Category("Historical Places","historic_site"));
        category.add(new Category("Museums","museum_10607452"));
        category.add(new Category("Mountains","mountain"));
        category.add(new Category("Forests","trees"));
        category.add(new Category("Caves","cave_7105096"));
        category.add(new Category("Camps","camping_6341788"));

        if (view!=null) {
            recyclerViewCategory = view.findViewById(R.id.catRec);
            recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            adapterCategory = new CategoryAdapter(category);
            recyclerViewCategory.setAdapter(adapterCategory);
        }




    }


}