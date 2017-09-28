package com.rafaelguimas.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Rafael on 27/09/2017.
 */

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StepViewsFactory(this.getApplicationContext());
    }
}
