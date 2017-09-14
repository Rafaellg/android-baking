package com.rafaelguimas.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.ToolbarControlView;
import com.rafaelguimas.bakingapp.adapter.IngredientsListAdapter;
import com.rafaelguimas.bakingapp.adapter.StepsListAdapter;
import com.rafaelguimas.bakingapp.fragment.RecipeDetailFragment;
import com.rafaelguimas.bakingapp.fragment.StepDetailFragment;
import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements ToolbarControlView, StepsListAdapter.OnStepItemClick {

    public static final String ARG_ITEM = "item_recipe";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

        if (getIntent().getExtras() != null) {
            mRecipe = getIntent().getExtras().getParcelable(ARG_ITEM);
        }

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.ARG_ITEM, mRecipe);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM, mRecipe);

            startActivity(intent);
        }

        // Set the background image
        Glide.with(this).load(mRecipe.getImage()).into(ivBackground);

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
    }

    @Override
    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void notifyOnStepItemClick(int selectedPosition) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_detail_container, StepDetailFragment.newInstance(new ArrayList<>(mRecipe.getSteps()), selectedPosition))
                .addToBackStack(null)
                .commit();
    }
}
