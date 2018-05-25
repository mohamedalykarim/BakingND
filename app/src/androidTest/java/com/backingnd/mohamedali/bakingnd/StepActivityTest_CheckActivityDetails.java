package com.backingnd.mohamedali.bakingnd;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.backingnd.mohamedali.bakingnd.DetailsActivityTest_CheckRecyclerViewITem.STEP_DESCRIPTION_2;
import static com.backingnd.mohamedali.bakingnd.DetailsActivityTest_CheckRecyclerViewITem.STEP_SHORT_DESCRIPTION_2;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class StepActivityTest_CheckActivityDetails {





    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testRecyclerView_TestContent(){

        /**
         * checking item at position 0
         */
        onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(0).perform(click());

        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));

        onView(withId(R.id.short_description)).check(matches(withText(containsString(STEP_SHORT_DESCRIPTION_2))));
        onView(withId(R.id.description)).check(matches(withText(containsString(STEP_DESCRIPTION_2))));





    }







}
