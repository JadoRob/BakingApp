package org.codelab.google.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import static org.codelab.google.bakingapp.RecipeWidgetConfig.KEY_BUTTON_TEXT;
import static org.codelab.google.bakingapp.RecipeWidgetConfig.SHARED_PREFS;


public class RecipeAppWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_RECIPE = "recipe name";
    private static final String TAG = RecipeAppWidgetProvider.class.getSimpleName();

    //this is NOT called when the widget is first placed on the screen. onUpdate is used when changes
    //are made or if the phone is restarted. The widget will then then be updated.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //retrieve recipe data (selected by user and passed from RecipeWidgetConfig)
        //to be displayed on the widget.
        for (int appwidgetId : appWidgetIds) {
            //create intent for opening baking app
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            String recipeName = prefs.getString(ACTION_RECIPE, "press me");
            String buttonText = prefs.getString(KEY_BUTTON_TEXT + appwidgetId, "press me");

            Intent serviceIntent = new Intent(context, RecipeWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appwidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            //displays layout in the widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            //watch for clicks then open MainActivity of baking app via passing the intent
            views.setOnClickPendingIntent(R.id.example_widget_button, pendingIntent);
            views.setRemoteAdapter(R.id.recipe_widget_list_view, serviceIntent);
            views.setCharSequence(R.id.example_widget_button, "setText", buttonText);

            appWidgetManager.updateAppWidget(appwidgetId, views);
            Log.i(TAG, "onUpdate triggered! " + recipeName);
        }
    }

    @Override
    //updates the listview of the widget
    public void onReceive(Context context, Intent intent) {
        String recipeName = intent.getStringExtra(ACTION_RECIPE);

        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, RecipeAppWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName),
                    R.id.recipe_widget_list_view);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            views.setCharSequence(R.id.example_widget_button, "setText", recipeName);
            appWidgetManager.updateAppWidget(componentName, views);
        }
        super.onReceive(context, intent);
    }
    public static void sendRefresh(Context context, String recipeName) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Log.i(TAG, "from sendRefresh method: " + recipeName);
        intent.putExtra(ACTION_RECIPE, recipeName);
        intent.setComponent(new ComponentName(context, RecipeAppWidgetProvider.class));
        context.sendBroadcast(intent);
    }
}
