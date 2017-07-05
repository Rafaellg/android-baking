package com.rafaelguimas.bakingapp.network;

import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafael on 05/07/2017.
 */

public class RecipePresenter {

    private static final String mBaseUrl = "https://d17h27t6h515a5.cloudfront.net/";

    public interface RecipePresenterCallback {
        void notifyServiceSuccess(List<Recipe> recipeList);

        void notifyServiceError();
    }

    private RecipePresenterCallback mRecipePresenterCallback;

    public RecipePresenter(RecipePresenterCallback callback) {
        mRecipePresenterCallback = callback;
    }

    public void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(RecipesService.class).getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipePresenterCallback.notifyServiceSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mRecipePresenterCallback.notifyServiceError();
            }
        });
    }
}
