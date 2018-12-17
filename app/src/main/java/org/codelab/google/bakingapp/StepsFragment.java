package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

import java.util.List;


public class StepsFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private MainViewModel mMainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.steps_fragment, container, false);
        final TextView instructions = v.findViewById(R.id.instructions);

        mMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mMainViewModel.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Recipe currentRecipe = recipes.get(1);
                List<Steps> steps = currentRecipe.getSteps();
                Steps currentStep = steps.get(1);
                Log.i(TAG, "from onchanged of DetailFragment: " + currentStep.getDescription());
                instructions.setText(currentStep.getDescription());

            }
        });

        return v;

    }
}
