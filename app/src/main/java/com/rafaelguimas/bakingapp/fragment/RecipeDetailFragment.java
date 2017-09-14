package com.rafaelguimas.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.ToolbarControlView;
import com.rafaelguimas.bakingapp.adapter.IngredientsListAdapter;
import com.rafaelguimas.bakingapp.adapter.StepsListAdapter;
import com.rafaelguimas.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepsListAdapter.OnStepItemClick {

    public static final String ARG_ITEM = "item_recipe";

    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;
    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    private Recipe mRecipe;
    private ToolbarControlView mListener;

    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailFragment.ARG_ITEM, recipe);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            mRecipe = getArguments().getParcelable(ARG_ITEM);
        }

        // Set recipe name as toolbar title
        mListener.setToolbarTitle(mRecipe.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Map the view items
        ButterKnife.bind(this, rootView);

        // Set the background image
        Glide.with(this).load(mRecipe.getImage()).into(ivBackground);

        if (mRecipe.getIngredients() != null) {
            // Create the adapters
            IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(mRecipe.getIngredients());

            // Setup RV
            rvIngredients.setAdapter(ingredientsListAdapter);
            rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        if (mRecipe.getSteps() != null) {
            // Create the adapter
            StepsListAdapter stepsListAdapter = new StepsListAdapter(mRecipe.getSteps(), this);

            // Setup RVs
            rvSteps.setAdapter(stepsListAdapter);
            rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarControlView) {
            mListener = (ToolbarControlView) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement " + ToolbarControlView.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void notifyOnStepItemClick(int selectedPosition) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.step_detail_container, StepDetailFragment.newInstance(new ArrayList<>(mRecipe.getSteps()), selectedPosition))
                .addToBackStack(null)
                .commit();
    }
}
