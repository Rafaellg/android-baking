package com.rafaelguimas.bakingapp.activity;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import com.rafaelguimas.bakingapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Before
    public void preTest() {
        RecipeListActivityTest recipeListActivityTest = new RecipeListActivityTest();
        recipeListActivityTest.recipeListActivityTest();
    }

    @Test
    public void recipeDetailActivityTest() {
        onView(allOf(withId(R.id.rv_ingredients), isDisplayed()));
        onView(allOf(withId(R.id.rv_steps), isDisplayed()));

        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.cv_step_detail))
                .check(matches(isDisplayed()));
    }
}
