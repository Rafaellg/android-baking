package com.rafaelguimas.bakingapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.adapter.RecipeDetailViewPagerAdapter;
import com.rafaelguimas.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    public static final String ARG_ITEM = "item_recipe";

    @BindView(R.id.tl_options)
    TabLayout tlOptions;
    @BindView(R.id.vw_options)
    ViewPager vwOptions;

    private Recipe mItem;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            mItem = getArguments().getParcelable(ARG_ITEM);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ButterKnife.bind(this, rootView);

        if (mItem != null) {
            RecipeDetailViewPagerAdapter adapter = new RecipeDetailViewPagerAdapter(getFragmentManager(), mItem);
            vwOptions.setAdapter(adapter);

            tlOptions.setupWithViewPager(vwOptions);
        }

        return rootView;
    }
}
