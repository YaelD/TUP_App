package com.example.tupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchAttractionsActivity extends AppCompatActivity {

    private SearchView searchViewAttractions;
    private RecyclerView attractionsRecView;
    private AttractionsSearchRecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_attractions);

        searchViewAttractions = findViewById(R.id.searchViewAttr);
        attractionsRecView = findViewById(R.id.searchAttrRecView);
        adapter = new AttractionsSearchRecAdapter(this);

        attractionsRecView.setAdapter(adapter);
        attractionsRecView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction("London eye", "Riverside Building, County Hall, London SE1 7PB, United Kingdom",
                "+44 20 7967 8021", "https://www.londoneye.com/", "1",
                "https://media.cntraveler.com/photos/55c8be0bd36458796e4ca38a/master/pass/london-eye-2-cr-getty.jpg"));
        attractions.add(new Attraction("Buckingham Palace", "London SW1A 1AA, United Kingdom",
                "+44 303 123 7300", "https://www.royal.uk/royal-residences-buckingham-palace", "2",
                "https://zamanturkmenistan.com.tm/wp-content/uploads/2021/04/buckingham-palace-london.jpg"));
        adapter.setAttractions(attractions);
    }
}