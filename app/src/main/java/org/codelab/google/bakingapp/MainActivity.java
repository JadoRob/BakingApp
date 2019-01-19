package org.codelab.google.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener {

    public static final String EXTRA_RECIPE = "Pass Recipe";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //RecipeFragment instantiated through activity_main.xml
        setContentView(R.layout.activity_main);

    }
    //interface to receive callback from RecipeFragment to start the RecipeActivity with an
    // associated ViewModel, to enable communication between the DetailFragment and StepsFragment. Position
    //is used to pass the selected recipe.
    @Override
    public void onRecipeSelected(int position, String recipeName) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE, position);
        RecipeAppWidgetProvider.sendRefresh(getApplicationContext(), recipeName);
        Log.i(TAG, "onRecipeSelected: " + recipeName);
        startActivity(intent);
    }
}
