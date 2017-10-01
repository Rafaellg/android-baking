package com.rafaelguimas.bakingapp.activity;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rafaelguimas.bakingapp.R;
import com.rafaelguimas.bakingapp.models.Recipe;
import com.rafaelguimas.bakingapp.models.Step;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    private ArrayList<Step> mStepList = new ArrayList<>(Recipe.mockObject().getSteps());
    private int mSelectedPosition = 0;

    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, StepDetailActivity.class);
                    result.putParcelableArrayListExtra(StepDetailActivity.ARG_STEP_LIST, mStepList);
                    result.putExtra(StepDetailActivity.ARG_SELECTED_POSITION, mSelectedPosition);
                    return result;
                }
            };

    @Test
    public void recipeDetailActivityTest() {
        onView(allOf(withId(R.id.tv_description), isDisplayed(), withText(mStepList.get(mSelectedPosition).getDescription())));
    }
}
