package com.rafaelguimas.bakingapp.network;

import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Rafael on 05/07/2017.
 */

public interface RecipesEndpoints {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
