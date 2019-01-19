package org.codelab.google.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import org.codelab.google.bakingapp.data.IngredientDao;
import org.codelab.google.bakingapp.data.Ingredients;
import org.codelab.google.bakingapp.data.IngredientsDatabase;

import java.util.List;

public class RecipeWidgetService extends RemoteViewsService {

    private static final String TAG = RecipeWidgetService.class.getSimpleName();
    private List<Ingredients> ingredients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetIngredientFactory(getApplicationContext(), intent);
    }

    class RecipeWidgetIngredientFactory implements RemoteViewsFactory {

        private Context context;
        //used if there are multiple widgets
        private int appWidgetId;
        //dummy data
        private String[] exampleData = {"one", "two", "three", "four", "five"};
        private String recipeName;
        IngredientsDatabase db;
        IngredientDao dao;

        RecipeWidgetIngredientFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            db = IngredientsDatabase.getInstance(context);
            dao = db.ingredientDao();
        }

        //fetch data and update the list view, also refreshes data when a change is recieved
        @Override
        public void onDataSetChanged() {

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_widget_item);
            views.setCharSequence(R.id.example_widget_button, "setText", recipeName);
            ingredients = dao.getAllIngredients();
            Log.i(TAG, "onDataSetChanged called from RecipeWidgetService! " + recipeName);
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            Log.i(TAG, "ingredients size = " + ingredients.size());
            SystemClock.sleep(500);
            return ingredients.size();

        }

        //adater that updates the widget ingredient list
        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_widget_item);
            String currentIngredient = buildIngredients(position);
            views.setTextViewText(R.id.recipe_widget_item_text, currentIngredient);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    //gets the current ingredient from the list and formats it into a string
    private String buildIngredients(int position) {
        StringBuilder makeList = new StringBuilder();
        makeList.append(ingredients.get(position).getQuantity()).append(" ");
        makeList.append(ingredients.get(position).getMeasure()).append(" - ");
        makeList.append(ingredients.get(position).getIngredient()).append("\n");
        return makeList.toString();
    }
}
