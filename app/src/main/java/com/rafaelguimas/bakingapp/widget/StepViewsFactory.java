package com.rafaelguimas.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.models.Step;

import java.util.List;

/**
 * Created by rafae on 25/09/2017.
 */

public class StepViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Step> mStepList;
    private Intent mIntent;

    public StepViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mIntent = intent;
        mStepList = intent.getParcelableArrayListExtra(RecipeWidgetRemoteViewsService.EXTRA_STEP_LIST);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mStepList = mIntent.getParcelableArrayListExtra(RecipeWidgetRemoteViewsService.EXTRA_STEP_LIST);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mStepList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Step step = mStepList.get(position);

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.steps_list_content);

        row.setTextViewText(R.id.tv_id, step.getId().toString());
        row.setTextViewText(R.id.tv_short_description, step.getShortDescription());

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
