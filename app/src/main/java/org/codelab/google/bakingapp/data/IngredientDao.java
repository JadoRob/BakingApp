package org.codelab.google.bakingapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredients ingredient);

    @Update
    void update(Ingredients ingredient);

    @Delete
    void delete(Ingredients ingredient);

    @Query("DELETE FROM ingredients_table")
    void deleteAllIngredients();

    @Query("SELECT * FROM ingredients_table ORDER BY ingredient")
    List<Ingredients> getAllIngredients();
}
