package com.rafaelguimas.bakingapp.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;

import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.network.RecipePresenter;
import com.rafaelguimas.bakingapp.widget.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.List;

public class RecipesService extends Service implements RecipePresenter.RecipePresenterCallback {

    public RecipesService() {
    }

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    public static ArrayList<Recipe> listItemList;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        new RecipePresenter(this).getRecipes();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void notifyGetRecipesSuccess(List<Recipe> recipeList) {
        populateWidget();
    }

    @Override
    public void notifyGetRecipesError() {
        // N/A
    }

    private void populateWidget() {
        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(RecipeWidgetProvider.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        sendBroadcast(widgetUpdateIntent);
        this.stopSelf();
    }
}
