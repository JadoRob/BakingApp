package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
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

public class RecipeFragment extends Fragment implements RecipeAdapter.OnItemClickListener {

    private static final String TAG = RecipeFragment.class.getSimpleName();
    private RecyclerView mRecyclerview;
    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainViewModel viewModel;

    //for communication with MainActivity or RecipeWidgetConfig
    OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position, String recipeName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mRecyclerview = getActivity().findViewById(R.id.recyclerview);
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new RecipeAdapter(recipes);
                mRecyclerview.setLayoutManager(mLayoutManager);
                mRecyclerview.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(RecipeFragment.this);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //checks and confirms interface listener with the MainActivity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onItemClick(int position) {
        String recipeName;
        viewModel.setCurrentRecipe(position);
        viewModel.select("details");
        recipeName = viewModel.getCurrentRecipeName();
        mCallback.onRecipeSelected(position, recipeName);

        SharedPreferences prefs = getActivity()
                .getSharedPreferences("Values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(recipeName, "recipeName");
        editor.apply();
    }
}
