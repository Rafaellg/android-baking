package com.rafaelguimas.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.activity.StepDetailActivity;
import com.rafaelguimas.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 25/09/2017.
 */

public class StepViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Step> mStepList;

    public StepViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mStepList = RecipeWidgetProvider.getStepsFromRecipe();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mStepList == null ? 0 : mStepList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Step step = mStepList.get(position);

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_step_list);

        row.setTextViewText(R.id.tv_id, step.getId().toString());
        row.setTextViewText(R.id.tv_short_description, step.getShortDescription());

        Intent fillInIntent = new Intent();
        fillInIntent.putParcelableArrayListExtra(StepDetailActivity.ARG_STEP_LIST, new ArrayList<Parcelable>(mStepList));
        fillInIntent.putExtra(StepDetailActivity.ARG_SELECTED_POSITION, position);
        row.setOnClickFillInIntent(R.id.ll_container, fillInIntent);

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
