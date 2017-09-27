package com.rafaelguimas.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Rafael on 27/09/2017.
 */

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    public static final String EXTRA_STEP_LIST = "STEP_LIST";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StepViewsFactory(this.getApplicationContext(), intent);
    }
}
