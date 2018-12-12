package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private RecyclerView mRecyclerview;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeFragment fragment = new RecipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


        //        mRecyclerview = findViewById(R.id.recyclerview);
//        mRecyclerview.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//
//        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//
//        mMainViewModel.getRecipeList().observe(this, new Observer<List<Recipe>>() {
//            @Override
//            public void onChanged(@Nullable List<Recipe> recipes) {
//                mAdapter = new RecipeAdapter(recipes);
//                mRecyclerview.setLayoutManager(mLayoutManager);
//                mRecyclerview.setAdapter(mAdapter);
//            }
//        });

    }
}
