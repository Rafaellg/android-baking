package com.rafaelguimas.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.network.RecipePresenter;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecipeIntentService extends IntentService implements RecipePresenter.RecipePresenterCallback {

    public static final String ACTION_FETCH_RECIPES = "com.rafaelguimas.bakingapp.widget.action.FETCH_RECIPES";
    public static final String EXTRA_NEXT = "com.rafaelguimas.bakingapp.widget.extra.NEXT";
    public static final String EXTRA_CURRENT_RECIPE = "com.rafaelguimas.bakingapp.widget.extra.CURRENT_RECIPE";

    private Recipe mCurrentRecipe;
    private boolean isNext;

    public RecipeIntentService() {
        super("RecipeIntentService");
    }

    public static void startActionFetchRecipes(Context context) {
        Intent intent = new Intent(context, RecipeIntentService.class);
        intent.setAction(ACTION_FETCH_RECIPES);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_RECIPES.equals(action)) {
                new RecipePresenter(this).getRecipes();
            }
        }
    }

    @Override
    public void notifyGetRecipesSuccess(List<Recipe> recipeList) {
        RecipeWidgetProvider.setRecipeList(recipeList);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsId = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetsId, 0);

        Log.i(RecipeIntentService.class.getSimpleName(), "############# RECIPE LIST FETCHED - SIZE:" + recipeList.size());
    }

    @Override
    public void notifyGetRecipesError() {
        // N/A
    }
}
