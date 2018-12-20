package org.codelab.google.bakingapp;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.codelab.google.bakingapp.data.Steps;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        final RecipeFragment recipeFragment = new RecipeFragment();
        final StepsFragment stepsFragment = new StepsFragment();
        final DetailFragment detailFragment = new DetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, recipeFragment).commit();

        viewModel.getList().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (s.equals("details")) {
                    transaction.replace(R.id.container, detailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (s.equals("steps")) {
                    transaction.replace(R.id.container, stepsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }
}
