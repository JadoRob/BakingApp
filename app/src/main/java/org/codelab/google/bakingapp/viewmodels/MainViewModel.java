package org.codelab.google.bakingapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
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
    private MutableLiveData<String> selected;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        //obtains the list of recipes from the repository
        mRecipeList = mRecipeRepository.getRecipeList();
        currentRecipe = new MutableLiveData<>();
        currentStep = new MutableLiveData<>();
        selected = new MutableLiveData<>();
        selected.setValue("details");
        Log.i(TAG, "from MainViewModel constructor: " + selected.getValue());
    }

    public void setCurrentStep(int position) {
        List<Steps> steps = currentRecipe.getValue().getSteps();
        currentStep.setValue(steps.get(position));
    }

    public void setCurrentRecipe(int position) {
        Recipe recipe;
        recipe = mRecipeList.getValue().get(position);
        currentRecipe.setValue(recipe);
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

    //checks for the default place holder, and returns true if it is using the default description.
    public Boolean checkDefaultIntro(Recipe currentRecipe) {
        final String DEFAULT_INTRO = "Recipe Introduction";
        String recipeIntro = currentRecipe.getSteps().get(0).getDescription();
        if (recipeIntro.equals(DEFAULT_INTRO)) {
            return true;
        }
        return false;
    }




}
