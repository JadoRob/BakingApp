package org.codelab.google.bakingapp.data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    public static final String TAG = RecipeDatabase.class.getSimpleName();
    public static final String DATABASE_NAME = "favorite_movies";
    private static volatile RecipeDatabase INSTANCE;

    public static RecipeDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    Log.i(TAG, "Generating Recipes Database");
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    RecipeDatabase.class,
                                    RecipeDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration().build();
                }
            }

        }
        Log.i(TAG, "Retrieving instance of Recipes Database.");
        return INSTANCE;
    }

}
