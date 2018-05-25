package com.backingnd.mohamedali.bakingnd;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.backingnd.mohamedali.bakingnd.Models.Recipe;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTests_TestRecipesListViewClicks {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);





    @Test
    public void RecipesItemTest(){
            onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(0).perform(click());
            onView(isRoot()).perform(ViewActions.pressBack());

            onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(1).perform(click());
            onView(isRoot()).perform(ViewActions.pressBack());


            onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(2).perform(click());
            onView(isRoot()).perform(ViewActions.pressBack());

            onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(3).perform(click());



    }




}
