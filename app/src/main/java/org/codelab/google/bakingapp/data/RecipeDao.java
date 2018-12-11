package org.codelab.google.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

public interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE name = :name")
    LiveData<Recipe> getRecipe(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("DELETE FROM recipes")
    void nukeTable();


}
