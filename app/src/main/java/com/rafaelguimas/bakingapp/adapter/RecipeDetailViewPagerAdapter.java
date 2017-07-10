package com.rafaelguimas.bakingapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.fragment.IngredientListFragment;
import com.rafaelguimas.bakingapp.fragment.StepsListFragment;
import com.rafaelguimas.bakingapp.models.Recipe;

/**
 * Created by rafae on 10/07/2017.
 */

public class RecipeDetailViewPagerAdapter extends FragmentPagerAdapter {

    private Recipe mRecipe;
    private Context mContext;

    public RecipeDetailViewPagerAdapter(FragmentManager fm, Recipe recipe, Context context) {
        super(fm);
        mRecipe = recipe;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IngredientListFragment.newInstance(mRecipe.getIngredients());
            case 1:
                return StepsListFragment.newInstance(mRecipe.getSteps());
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.label_ingredients);
            case 1:
                return mContext.getString(R.string.label_steps);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
