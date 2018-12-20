package org.codelab.google.bakingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import org.codelab.google.bakingapp.MainActivity;
import org.codelab.google.bakingapp.data.Recipe;
import org.codelab.google.bakingapp.data.RecipeRepository;
import org.codelab.google.bakingapp.data.Steps;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private RecipeRepository mRecipeRepository;
    private LiveData<List<Recipe>> mRecipeList;
    private MutableLiveData<Recipe> currentRecipe;
    private MutableLiveData<Steps> currentStep;
    private MutableLiveData<String> change;



    public MainViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        mRecipeList = mRecipeRepository.getRecipeList();
        currentRecipe = new MutableLiveData<>();
        currentStep = new MutableLiveData<>();
        change = new MutableLiveData<>();

        change.setValue("test");
        Log.i(TAG, "from MainViewModel constructor: " + change.getValue());


    }

    public void setCurrentStep(int position) {
        String recipeName = currentRecipe.getValue().getName();
        List<Steps> steps = currentRecipe.getValue().getSteps();
        currentStep.setValue(steps.get(position));
        Log.i(TAG, "from setCurrentStep in viewmodel: " + currentStep.getValue().getShortDescription());
    }

    public LiveData<Steps> getCurrentStep() {
        return currentStep;
    }

    public void setCurrentRecipe(int position) {
        currentRecipe.setValue(mRecipeList.getValue().get(position));
    }

    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }

    public LiveData<String> getList() {
        return change;
    }

    public void setList(String page) {
        change.setValue(page);
    }




}
