package org.codelab.google.bakingapp.data;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class RecipeRepository {

    private static RecipeRepository instance;
    private static final Object LOCK = new Object();
    private Context mContext;
    public static final String TAG = RecipeRepository.class.getSimpleName();

    RecipeRepository(Application application) {

        mContext = application;
    }

    public static RecipeRepository getInstance(Application application) {
        Log.i(TAG, "Retrieving the repository.");
        if (instance == null) {
            synchronized (LOCK) {
                instance = new RecipeRepository(application);
                Log.i(TAG, "No repository found, initializing.");
            }
        }
        return(instance);
    }

}
