package com.example.health_tracker;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class SuggestionActivityTest {

    @Rule
    public ActivityTestRule<SuggestionActivity> mActivityTestRule =
            new ActivityTestRule<SuggestionActivity>(SuggestionActivity.class);
    public SuggestionActivity suggestionActivity = null;

    @Before
    public void setUp() throws Exception {
        suggestionActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void UiLoad() {

        assertNotNull(suggestionActivity);
    }

    @Test
    public void RecyclerTest() {

        onView(withId(R.id.RecyclerViewSuggestion)).check(matches(isDisplayed()));
        // perform click on view at 3rd position in RecyclerView
        onView(withId(R.id.RecyclerViewSuggestion))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }


    @After
    public void tearDown() throws Exception {
        suggestionActivity = null;
    }
}