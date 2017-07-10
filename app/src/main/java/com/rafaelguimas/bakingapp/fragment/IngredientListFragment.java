package com.rafaelguimas.bakingapp.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.adapter.IngredientsListAdapter;
import com.rafaelguimas.bakingapp.adapter.StepsListAdapter;
import com.rafaelguimas.bakingapp.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListFragment extends Fragment {

    private static final String ARG_ITEM = "item_ingredients";

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    private ArrayList<Ingredient> mIngredientList;

    public IngredientListFragment() {
        // Required empty public constructor
    }

    public static IngredientListFragment newInstance(List<Ingredient> ingredientList) {
        IngredientListFragment fragment = new IngredientListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM, new ArrayList<>(ingredientList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIngredientList = getArguments().getParcelableArrayList(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ButterKnife.bind(this, view);

        if (mIngredientList != null) {
            // Create the adapters
            IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(mIngredientList);

            // Setup RVs with adapters
            rvIngredients.setAdapter(ingredientsListAdapter);
            rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));

            // Add the divider to the ingredients RV
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            rvIngredients.addItemDecoration(itemDecoration);
        }

        return view;
    }

}
