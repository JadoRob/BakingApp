package org.codelab.google.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;
import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.viewmodels.MainViewModel;
import java.util.List;

public class RecipeWidgetConfig extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener {

    public static final String SHARED_PREFS = "prefs";
    public static final String KEY_BUTTON_TEXT = "keyButtonText";

    //Once widget is created, the app widget ID will be passed here
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private List<Ingredients> ingredients;
    private String recipeName;
    private MainViewModel viewModel;

    //When the widget is first placed on screen, it launches the RecipeActivity and RecipeFragment
    //for the user to select a recipe and display the ingredients
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_widget_config);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //retrieve the widget ID using intent and pass to appWidgetId
        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        //sets resultValue as canceled if the user hits the back button before choosing a recipe
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        //if the ID from the widget is not passed, (appWidgetId is invalid) close the activity.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        RecipeFragment recipeFragment = new RecipeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, recipeFragment).commit();
    }

        public void confirmConfiguration(String recipeName, List<Ingredients> ingredients) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent serviceIntent = new Intent(this, RecipeWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        //updating widget views and refreshing listview
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.recipe_widget);
        views.setOnClickPendingIntent(R.id.example_widget_button, pendingIntent);
        views.setCharSequence(R.id.example_widget_button, "setText", recipeName);
        views.setRemoteAdapter(R.id.recipe_widget_list_view, serviceIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @Override
    public void onRecipeSelected(int position,  String recipeName) {
        ingredients = viewModel.getIngredients();
        confirmConfiguration(recipeName, ingredients);
    }
}
