package org.codelab.google.bakingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.RecipeRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RecipeRepository mRecipeRepository;
    private LiveData<List<Recipe>> mRecipeList;
    private MutableLiveData<Recipe> currentRecipe;



    public MainViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        mRecipeList = mRecipeRepository.getRecipeList();


    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }

    public void selectRecipe(Recipe recipe) {
        currentRecipe.setValue(recipe);
    }

    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }



}
