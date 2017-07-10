package com.rafaelguimas.bakingapp.fragment;


import android.os.Bundle;
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
import com.rafaelguimas.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListFragment extends Fragment implements StepsListAdapter.OnStepItemClick {

    private static final String ARG_ITEM = "item_steps";

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    private ArrayList<Step> mStepsList;

    public StepsListFragment() {
        // Required empty public constructor
    }

    public static StepsListFragment newInstance(List<Step> ingredientList) {
        StepsListFragment fragment = new StepsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM, new ArrayList<>(ingredientList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepsList = getArguments().getParcelableArrayList(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);

        ButterKnife.bind(this, view);

        if (mStepsList != null) {
            // Create the adapter
            StepsListAdapter stepsListAdapter = new StepsListAdapter(mStepsList, this);

            // Setup RVs with adapters
            rvSteps.setAdapter(stepsListAdapter);
            rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }

    @Override
    public void notifyOnStepItemClick(Step item) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_detail_container, StepDetailFragment.newInstance(item))
                .addToBackStack(null)
                .commit();
    }
}
