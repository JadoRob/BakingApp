package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.viewadapters.RecipeAdapter;
import org.codelab.google.bakingapp.viewadapters.StepsAdapter;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

import java.util.List;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainViewModel mMainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment, container, false);
        final TextView ingredients = v.findViewById(R.id.ingredients);

        mMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mMainViewModel.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Recipe currentRecipe = recipes.get(0);
                Log.i(TAG, "from onchanged of DetailFragment: " + currentRecipe.getName());
                ingredients.setText(buildIngredients(currentRecipe.getIngredients()));
                mRecyclerView = getActivity().findViewById(R.id.recyclerview);
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new StepsAdapter(recipes.get(0));
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);


            }
        });
        return v;
    }

    private String buildIngredients(List<Ingredients> ingredients) {
        StringBuilder makeList = new StringBuilder();
        for (Ingredients list : ingredients) {
            makeList.append(list.getQuantity()).append(" ");
            makeList.append(list.getMeasure()).append(" - ");
            makeList.append(list.getIngredient()).append("\n");
        }

        return makeList.toString();

    }

}
