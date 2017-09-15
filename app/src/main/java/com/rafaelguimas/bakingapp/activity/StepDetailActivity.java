package com.rafaelguimas.bakingapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.ToolbarControlView;
import com.rafaelguimas.bakingapp.fragment.StepDetailFragment;
import com.rafaelguimas.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements ToolbarControlView {

    public static final String ARG_STEP_LIST = "arg_step_list";
    public static final String ARG_SELECTED_POSITION = "arg_selected_position";

    private ArrayList<Step> mStepList;
    private int mSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mStepList = getIntent().getExtras().getParcelableArrayList(ARG_STEP_LIST);
            mSelectedPosition = getIntent().getExtras().getInt(ARG_SELECTED_POSITION);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity using a fragment transaction.
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, StepDetailFragment.newInstance(mStepList, mSelectedPosition))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
