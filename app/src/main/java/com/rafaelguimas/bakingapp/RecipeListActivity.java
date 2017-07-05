package com.rafaelguimas.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.rafaelguimas.bakingapp.adapter.SimpleItemRecyclerViewAdapter;
import com.rafaelguimas.bakingapp.dummy.DummyContent;
import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.network.RecipePresenter;

import java.util.List;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements RecipePresenter.RecipePresenterCallback, SimpleItemRecyclerViewAdapter.OnItemClick {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private RecipePresenter mRecipesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Call service
        mRecipesPresenter = new RecipePresenter(this);
        mRecipesPresenter.getRecipes();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS, mTwoPane, this));
    }

    @Override
    public void notifyServiceSuccess(List<Recipe> recipeList) {
        Toast.makeText(this, "YES! " + recipeList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyServiceError() {
        Toast.makeText(this, "DEU RUIM!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyOnItemClick(String itemId) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, itemId);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, itemId);

            startActivity(intent);
        }
    }
}
