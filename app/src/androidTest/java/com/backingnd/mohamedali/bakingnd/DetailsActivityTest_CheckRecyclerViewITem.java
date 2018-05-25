package com.backingnd.mohamedali.bakingnd;


import android.content.ClipData;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest_CheckRecyclerViewITem {

    public static final String INGREDIENT1_SHORT_DESCRIPTION = "Nutella or other chocolate-hazelnut spread";
    public static final String INGREDIENT2_SHORT_DESCRIPTION = "Mascapone Cheese(room temperature)";

    public static final String STEP_SHORT_DESCRIPTION_1 = "Recipe Introduction";
    public static final String STEP_DESCRIPTION_1 = "Recipe Introduction";

    public static final String STEP_SHORT_DESCRIPTION_2 = "Start filling";
    public static final String STEP_DESCRIPTION_2 = "4. Beat together the nutella, mascarpone,";



    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testRecyclerView_TestContent(){

        /**
         * checking item at position 0
         */
        onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(0).perform(click());
        onView(withId(R.id.steps_recycler_view)).check(matches(hasDescendant(withText(containsString(INGREDIENT1_SHORT_DESCRIPTION)))));
        onView(withId(R.id.steps_recycler_view)).check(matches(hasDescendant(withText(containsString(INGREDIENT2_SHORT_DESCRIPTION)))));

        onView(withId(R.id.steps_recycler_view)).check(matches(hasDescendant(withText(containsString(STEP_SHORT_DESCRIPTION_1)))));
        onView(withId(R.id.steps_recycler_view)).check(matches(hasDescendant(withText(containsString(STEP_DESCRIPTION_1)))));


        /**
         * checking item at position 4
         */

        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));
        onView(withId(R.id.steps_recycler_view)).check(matches(hasDescendant(withText(containsString(STEP_SHORT_DESCRIPTION_2)))));
        onView(withId(R.id.steps_recycler_view)).check(matches(hasDescendant(withText(containsString(STEP_DESCRIPTION_2)))));

        /**
         * Testing clicks
         */

        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));




    }







}
