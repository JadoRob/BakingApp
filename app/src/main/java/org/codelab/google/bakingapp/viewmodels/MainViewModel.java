package org.codelab.google.bakingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.RecipeRepository;
import org.codelab.google.bakingapp.data.Steps;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private RecipeRepository mRecipeRepository;
    //used to store ingredients for the widget;
    private List<Ingredients> recipeIngredients;
    private LiveData<List<Recipe>> mRecipeList;
    private MutableLiveData<Recipe> currentRecipe;
    private MutableLiveData<Steps> currentStep;
    private MutableLiveData<String> selected;
    private long playbackPosition = 0;
    private List<String> recipeVideos;

    //no longer relevant, remove this and putExtra from
    private boolean returnToWidget;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        //obtains the list of recipes from the repository
        mRecipeList = mRecipeRepository.getRecipeList();
        //this is also used to update the widget ingredients
        currentRecipe = new MutableLiveData<>();
        currentStep = new MutableLiveData<>();
        selected = new MutableLiveData<>();
        selected.setValue("details");
        Log.i(TAG, "from MainViewModel constructor: " + selected.getValue());
        //purge the previous ingredient list for a new one to be displayed for the widget
        mRecipeRepository.deleteAllIngredients();
    }

    public void setWidgetIngredientList() {
        //purge previous list

    }

    public void setCurrentStep(int position) {
        //resets the playback position for any videos that was previously played
        playbackPosition = 0;
        List<Steps> steps = currentRecipe.getValue().getSteps();
        currentStep.setValue(steps.get(position));
    }

    public void setCurrentRecipe(int position) {
        Recipe recipe;
        recipe = mRecipeList.getValue().get(position);
        currentRecipe.setValue(recipe);
        recipeVideos = new ArrayList<>();
        //saves the list of ingredients for the widget
        List<Ingredients> ingredients = recipe.getIngredients();
        Log.i(TAG, "ingredients size: " + ingredients.size());
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                mRecipeRepository.insert(ingredients.get(i));
            }
        } else {
            Log.i(TAG, "Ingredients is null!");
        }
        //gets all the videos of the current recipe and passes to recipeVideos
        for (int i = 0; i < currentRecipe.getValue().getSteps().size(); i++) {
            List<Steps> steps = currentRecipe.getValue().getSteps();
            Log.i(TAG, "Recipe video: " + steps.get(i).getVideoURL());
            recipeVideos.add(steps.get(i).getVideoURL());
        }
    }

    public void checkLaunchedFromWidget(boolean returnToWidget) {
        this.returnToWidget = returnToWidget;
    }

    public LiveData<Steps> getCurrentStep() {
        return currentStep;
    }

    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }

    //used as a trigger for the fragments to determine what is displayed
    public LiveData<String> getSelected() {
        return selected;
    }

    public void select(String page) {
        selected.setValue(page);
    }

    public String getCurrentRecipeName() {
        return currentRecipe.getValue().getName();
    }

    //returns ingredients for the widget
    public List<Ingredients> getIngredients() {
        recipeIngredients = currentRecipe.getValue().getIngredients();
        return recipeIngredients;
    }

    public void setPlaybackPosition(long position) {
        this.playbackPosition = position;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }
}
