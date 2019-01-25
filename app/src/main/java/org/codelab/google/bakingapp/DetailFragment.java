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
import org.codelab.google.bakingapp.viewadapters.StepsAdapter;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;
import java.util.List;

public class DetailFragment extends Fragment implements StepsAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private StepsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainViewModel viewModel;
    private static final String TAG = DetailFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment, container, false);
        final TextView ingredients = v.findViewById(R.id.ingredients);
        //instantiate ViewModel from the associated activity (RecipeActivity)
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        Log.i(TAG, "from onCreate: " + viewModel.getSelected().getValue());
        //get the chosen recipe and populate the list adapter using LiveData
        viewModel.getCurrentRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                ingredients.setText(buildIngredients(recipe.getIngredients()));
                mRecyclerView = getActivity().findViewById(R.id.recyclerview);
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new StepsAdapter(recipe);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(DetailFragment.this);
            }
        });
        return v;
    }
    //creates and returns a string with the list of ingredients
    private String buildIngredients(List<Ingredients> ingredients) {
        StringBuilder makeList = new StringBuilder();
        for (Ingredients list : ingredients) {
            makeList.append(list.getQuantity()).append(" ");
            makeList.append(list.getMeasure()).append(" - ");
            makeList.append(list.getIngredient()).append("\n");
        }
        return makeList.toString();
    }
    @Override
    public void onItemClick(int position) {
        viewModel.setCurrentStep(position);
        viewModel.select("steps");
    }
}
