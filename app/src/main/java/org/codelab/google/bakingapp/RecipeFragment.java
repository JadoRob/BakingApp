package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.viewadapters.RecipeAdapter;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

import java.util.List;

public class RecipeFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainViewModel mMainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_fragment, container, false);

        mMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mMainViewModel.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mRecyclerview = getActivity().findViewById(R.id.recyclerview);
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new RecipeAdapter(recipes);
                mRecyclerview.setLayoutManager(mLayoutManager);
                mRecyclerview.setAdapter(mAdapter);
            }
        });

        return v;
    }
}
