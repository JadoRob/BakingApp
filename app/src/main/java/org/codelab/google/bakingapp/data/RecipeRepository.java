package org.codelab.google.bakingapp.data;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.codelab.google.bakingapp.network.JsonRecipes;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RecipeRepository {

    private static RecipeRepository instance;
    private static final Object LOCK = new Object();
    private static final String TAG = RecipeRepository.class.getSimpleName();
    private Context context;
    private ConnectivityManager cm;
    private NetworkInfo networkInfo;
    private boolean internetAccess;
    private MutableLiveData<List<Recipe>> mRecipeList;
    private List<Ingredients> ingredients;
    private IngredientDao ingredientDao;

    RecipeRepository(Application application) {

        IngredientsDatabase database = IngredientsDatabase.getInstance(application);
        ingredientDao = database.ingredientDao();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ingredients = ingredientDao.getAllIngredients();
            }
        });
        thread.start();

        context = application;
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();
        internetAccess = networkInfo != null && networkInfo.isConnected();

        if (!internetAccess) {
            Toast.makeText(context, "Unable to access internet.", Toast.LENGTH_SHORT).show();
        }
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

    public LiveData<List<Recipe>> getRecipeList() {
        if (mRecipeList == null) {
            mRecipeList = new MutableLiveData<>();
            lookupRecipes();
        }
        return mRecipeList;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void insert(Ingredients ingredients) {
        new InsertIngredientAsyncTask(ingredientDao).execute(ingredients);
        Log.i(TAG, "Writing to Database: " + ingredients.getIngredient());
    }

    public void update(Ingredients ingredients) {
        new UpdateIngredientAsyncTask(ingredientDao).execute(ingredients);
    }

    public void delete(Ingredients ingredients) {
        new DeleteIngredientAsyncTask(ingredientDao).execute(ingredients);
    }

    public void deleteAllIngredients() {
        new DeleteAllIngredientsAsyncTask(ingredientDao).execute();
    }

    private void lookupRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonRecipes jsonRecipes = retrofit.create(JsonRecipes.class);
        Call<List<Recipe>> call = jsonRecipes.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    String responseCode = "Code: " + response.code();
                    Log.i(TAG, responseCode);
                    return;
                }
                mRecipeList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    private static class InsertIngredientAsyncTask extends AsyncTask<Ingredients, Void, Void> {
        private IngredientDao ingredientDao;

        private InsertIngredientAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Ingredients... ingredients) {
            ingredientDao.insert(ingredients[0]);
            return null;
        }
    }

    private static class UpdateIngredientAsyncTask extends AsyncTask<Ingredients, Void, Void> {
        private IngredientDao ingredientDao;

        private UpdateIngredientAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Ingredients... ingredients) {
            ingredientDao.update(ingredients[0]);
            return null;
        }
    }

    private static class DeleteIngredientAsyncTask extends AsyncTask<Ingredients, Void, Void> {
        private IngredientDao ingredientDao;

        private DeleteIngredientAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Ingredients... ingredients) {
            ingredientDao.delete(ingredients[0]);
            return null;
        }
    }

    private static class DeleteAllIngredientsAsyncTask extends AsyncTask<Void, Void, Void> {
        private IngredientDao ingredientDao;

        private DeleteAllIngredientsAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ingredientDao.deleteAllIngredients();
            return null;
        }
    }
}
