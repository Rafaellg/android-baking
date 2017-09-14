package com.rafaelguimas.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.ToolbarControlView;
import com.rafaelguimas.bakingapp.adapter.RecipesListAdapter;
import com.rafaelguimas.bakingapp.fragment.RecipeDetailFragment;
import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.network.RecipePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements RecipePresenter.RecipePresenterCallback, RecipesListAdapter.OnItemClick, ToolbarControlView {

    @BindView(R.id.rv_recipes)
    RecyclerView rv_recipes;
    @BindView(R.id.av_loader)
    LottieAnimationView avLoader;

    private List<Recipe> mRecipeList;
    private RecipePresenter mRecipesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        // Call the recipes service
        if (mRecipeList == null) {
            mRecipesPresenter = new RecipePresenter(this);
            mRecipesPresenter.getRecipes();
        } else {
            notifyGetRecipesSuccess(mRecipeList);
        }
    }

    @Override
    public void notifyGetRecipesSuccess(List<Recipe> recipeList) {
        // Hide loader
        avLoader.setVisibility(View.GONE);

        // Saves the list
        mRecipeList = recipeList;

        // Sets up the RV
        assert rv_recipes != null;
        rv_recipes.setAdapter(new RecipesListAdapter(recipeList, this));
    }

    @Override
    public void notifyGetRecipesError() {
        // Show empty animation
        avLoader.setAnimation("shrug.json");
    }

    @Override
    public void notifyOnRecipeItemClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.ARG_ITEM, recipe);
        startActivity(intent);
    }

    @Override
    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
