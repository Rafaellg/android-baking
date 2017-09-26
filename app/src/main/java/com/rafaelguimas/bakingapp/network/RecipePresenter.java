package com.rafaelguimas.bakingapp.network;

import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.ArrayList;
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
    private RecipePresenterCallback mRecipePresenterCallback;

    public RecipePresenter(RecipePresenterCallback callback) {
        mRecipePresenterCallback = callback;
    }

    public void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(RecipesEndpoints.class).getRecipes().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                response.body().get(0).setImage("http://del.h-cdn.co/assets/16/32/1600x800/landscape-1470773544-delish-nutella-cool-whip-pie-1.jpg");
                response.body().get(1).setImage("http://d2gk7xgygi98cy.cloudfront.net/4-3-large.jpg");
                response.body().get(2).setImage("https://dessertswithbenefits.com/wp-content/uploads/2014/01/33.jpg");
                response.body().get(3).setImage("https://www.petitgastro.com.br/wp-content/uploads/2016/04/final2-Medium-1024x682.jpg");
                mRecipePresenterCallback.notifyGetRecipesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                mRecipePresenterCallback.notifyGetRecipesError();
            }
        });
    }

    public interface RecipePresenterCallback {
        void notifyGetRecipesSuccess(List<Recipe> recipeList);

        void notifyGetRecipesError();
    }
}
