package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.e.maintabactivity.adapters.ReviewsListAdapter;

public class ReviewsList extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private String[] reviews = {"Reviewkjbgsb svjsgjsv sjlvsvs vsdvjls 1",  "sbfks skbskbvs vskjbvsjkv 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list);

        mRecyclerView = findViewById(R.id.activity_reviews_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new ReviewsListAdapter(this, reviews));
    }
}
