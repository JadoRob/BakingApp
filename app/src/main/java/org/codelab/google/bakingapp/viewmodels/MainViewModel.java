package org.codelab.google.bakingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.RecipeRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RecipeRepository mRecipeRepository;
    private LiveData<List<Recipe>> mRecipeList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        mRecipeList = mRecipeRepository.getRecipeList();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }

}
