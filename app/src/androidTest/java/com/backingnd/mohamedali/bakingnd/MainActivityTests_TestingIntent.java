package com.backingnd.mohamedali.bakingnd;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Parcelable;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTests_TestingIntent {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);


    @Before
    public void stupAllExternalIntent(){
        intending(not(isInternal()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));
    }



    @Test
    public void IntentToDetailsActivity(){
        onData(anything()).inAdapterView(withId(R.id.recipes_list_view)).atPosition(0).perform(click());
        intended(allOf(
                hasExtraWithKey(Intent.EXTRA_COMPONENT_NAME),
                isInternal()

        ));
    }




}
