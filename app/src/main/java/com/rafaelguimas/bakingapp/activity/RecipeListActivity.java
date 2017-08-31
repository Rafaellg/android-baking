package com.rafaelguimas.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.adapter.RecipesListAdapter;
import com.rafaelguimas.bakingapp.fragment.RecipeDetailFragment;
import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.network.RecipePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements RecipePresenter.RecipePresenterCallback, RecipesListAdapter.OnItemClick {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recipe_list)
    RecyclerView recyclerView;
    @BindView(R.id.av_loader)
    LottieAnimationView avLoader;

    private boolean mTwoPane;
    private List<Recipe> mRecipeList;

    private RecipePresenter mRecipesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Call service
        if (mRecipeList == null) {
            mRecipesPresenter = new RecipePresenter(this);
            mRecipesPresenter.getRecipes();
        } else {
            notifyServiceSuccess(mRecipeList);
        }
    }

    private void setupRecyclerView(List<Recipe> recipeList) {
        recyclerView.setAdapter(new RecipesListAdapter(recipeList, this));
    }

    @Override
    public void notifyServiceSuccess(List<Recipe> recipeList) {
        // Hide loader
        avLoader.setVisibility(View.GONE);

        mRecipeList = recipeList;

        assert recyclerView != null;
        setupRecyclerView(recipeList);
    }

    @Override
    public void notifyServiceError() {
        // Show empty animation
        avLoader.setAnimation("shrug.json");
    }

    @Override
    public void notifyOnRecipeItemClick(Recipe recipe) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.ARG_ITEM, recipe);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM, recipe);

            startActivity(intent);
        }
    }
}
