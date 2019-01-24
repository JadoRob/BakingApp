package org.codelab.google.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;
import static org.codelab.google.bakingapp.MainActivity.EXTRA_RECIPE;

public class RecipeActivity extends AppCompatActivity {

    boolean returnToWidget = false;
    MainViewModel viewModel;
    private static final String TAG = RecipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_recipe);
        final Intent intent = getIntent();
        int position = intent.getIntExtra(EXTRA_RECIPE, 0);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        //removes from backstack in case orientation is changed when navigating back from
        // steps list
        fragmentManager.popBackStack();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setCurrentRecipe(position);
        viewModel.setWidgetIngredientList();
        viewModel.checkLaunchedFromWidget(returnToWidget);

        //Sets up the trigger in ViewModel to determine which fragment will show.
        viewModel.getSelected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String selected) {
                StepsFragment stepsFragment = new StepsFragment();
                DetailFragment detailFragment = new DetailFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (selected.equals("details")) {
                    //pressing back button goes back to RecipeActivity and loads RecipeFragment
                    transaction.replace(R.id.container, detailFragment).commit();
                } else if (selected.equals("steps")) {
                    //checks to see if the tablet/landscape xml is used and displays the steps on
                    //the view to the right.
                    if (findViewById(R.id.tablet_landscape) != null) {
                        transaction.replace(R.id.dual_container, stepsFragment);
                        Log.i(TAG, "Orientation: tablet/landscape");
                        if (savedInstanceState != null) {
                            transaction.replace(R.id.container, detailFragment);
                            transaction.commit();
                        } else {
                            transaction.commit();
                        }
                    } else {
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.container, stepsFragment).commit();
                        Log.i(TAG, "Orientation: phone");
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (viewModel.getSelected().getValue().equals("steps")) {
            viewModel.select("details");
        }
    }
}
