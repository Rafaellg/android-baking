package com.rafaelguimas.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.ToolbarControlView;
import com.rafaelguimas.bakingapp.adapter.IngredientsListAdapter;
import com.rafaelguimas.bakingapp.adapter.StepsListAdapter;
import com.rafaelguimas.bakingapp.fragment.StepDetailFragment;
import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements ToolbarControlView, StepsListAdapter.OnStepItemClick {

    public static final String ARG_ITEM = "item_recipe";

    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;
    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    private boolean mTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mRecipe = getIntent().getExtras().getParcelable(ARG_ITEM);
        }

        getSupportActionBar().setTitle(mRecipe.getName());

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Set the background image
        Glide.with(this)
                .load(mRecipe.getImage())
                .placeholder(R.drawable.img_food_placeholder_repeat)
                .into(ivBackground);

        if (mRecipe.getIngredients() != null) {
            // Create the adapters
            IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(mRecipe.getIngredients());

            // Setup RV
            rvIngredients.setAdapter(ingredientsListAdapter);
            rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        }

        if (mRecipe.getSteps() != null) {
            // Create the adapter
            StepsListAdapter stepsListAdapter = new StepsListAdapter(mRecipe.getSteps(), this);

            // Setup RVs
            rvSteps.setAdapter(stepsListAdapter);
            rvSteps.setLayoutManager(new LinearLayoutManager(this));
        }

        if (mTwoPane) {
            notifyOnStepItemClick(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, RecipeDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void notifyOnStepItemClick(int selectedPosition) {
        if (mTwoPane) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_detail_container, StepDetailFragment.newInstance(new ArrayList<>(mRecipe.getSteps()), selectedPosition))
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.ARG_STEP_LIST, new ArrayList<>(mRecipe.getSteps()));
            intent.putExtra(StepDetailActivity.ARG_SELECTED_POSITION, selectedPosition);

            startActivity(intent);
        }
    }
}
