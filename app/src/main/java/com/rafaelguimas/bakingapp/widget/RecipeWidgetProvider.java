package com.rafaelguimas.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.activity.StepDetailActivity;
import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.models.Step;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String DATA_FETCHED = "DATA_FETCHED";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_PREVIOUSLY = "ACTION_PREVIOUSLY";

    private static List<Recipe> mRecipeList;
    private static int mPosition = 0;
    private static Class<? extends RecipeWidgetProvider> mClass;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeIntentService.startActionFetchRecipes(context);
        mClass = getClass();
    }

    public static void setRecipeList(List<Recipe> recipeList) {
        mRecipeList = recipeList;
    }

    public static List<Step> getStepsFromRecipe() {
        return mRecipeList.get(mPosition).getSteps();
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, int position) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, position);
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int position) {
        mPosition = position;
        Recipe recipe = mRecipeList.get(position);

        // Create the RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_widget);
        views.setTextViewText(R.id.tv_recipe_title, recipe.getName());

        // Set's up the previously action
        if (position > 0) {
            views.setViewVisibility(R.id.iv_previously, View.VISIBLE);
            Intent previouslyIntent = new Intent(context, mClass);
            previouslyIntent.setAction(ACTION_PREVIOUSLY);
            PendingIntent previouslyPendingIntent = PendingIntent.getBroadcast(context, 0, previouslyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.iv_previously, previouslyPendingIntent);
        } else {
            views.setViewVisibility(R.id.iv_previously, View.GONE);
        }

        // Set's up the next action
        if (position < mRecipeList.size() - 1) {
            views.setViewVisibility(R.id.iv_next, View.VISIBLE);
            Intent nextIntent = new Intent(context, mClass);
            nextIntent.setAction(ACTION_NEXT);
            PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.iv_next, nextPendingIntent);
        } else {
            views.setViewVisibility(R.id.iv_next, View.GONE);
        }

        // Set's up the listview
        Intent adapterIntent = new Intent(context, RecipeWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.lv_ingredients, adapterIntent);

        // template to handle the click listener for each item
        Intent clickIntentTemplate = new Intent(context, StepDetailActivity.class);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_ingredients, clickPendingIntentTemplate);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        Log.i(RecipeWidgetProvider.class.getSimpleName(), "############# WIDGET UPDATED WITH RECIPE: " + recipe.getName());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetsId = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

        switch (intent.getAction()) {
            case ACTION_NEXT:
                ++mPosition;
                updateAppWidgets(context, appWidgetManager, appWidgetsId, mPosition);
                break;
            case ACTION_PREVIOUSLY:
                --mPosition;
                updateAppWidgets(context, appWidgetManager, appWidgetsId, mPosition);
                break;
        }

        switch (intent.getAction()) {
            case ACTION_NEXT:
            case ACTION_PREVIOUSLY:
                // refresh all your widgets
                AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                ComponentName cn = new ComponentName(context, RecipeWidgetProvider.class);
                mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.lv_ingredients);
                break;
        }

        Log.i(RecipeWidgetProvider.class.getSimpleName(), "############# ON RECEIVE ACTION: " + intent.getAction());

        super.onReceive(context, intent);
    }
}