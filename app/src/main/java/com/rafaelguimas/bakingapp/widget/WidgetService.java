package com.rafaelguimas.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.network.RecipePresenter;

import java.util.List;

/**
 * Created by rafae on 25/09/2017.
 */

public class WidgetService extends RemoteViewsService implements RecipePresenter.RecipePresenterCallback {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        RecipePresenter presenter = new RecipePresenter(this);
        return (new RecipeViewsFactory(this.getApplicationContext(), intent));
    }

    @Override
    public void notifyGetRecipesSuccess(List<Recipe> recipeList) {

    }

    @Override
    public void notifyGetRecipesError() {

    }
}
