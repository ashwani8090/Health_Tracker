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

public class TopicsActivityTest {

    @Rule
    public ActivityTestRule<TopicsActivity> mActivityTestRule =
            new ActivityTestRule<TopicsActivity>(TopicsActivity.class);
    public TopicsActivity topicsActivity = null;

    @Before
    public void setUp() throws Exception {
        topicsActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void UiLoad() {

        assertNotNull(topicsActivity);
    }

    @Test
    public void RecyclerTest() {

        onView(withId(R.id.RecyclerViewTopics)).check(matches(isDisplayed()));
        // perform click on view at 3rd position in RecyclerView
        onView(withId(R.id.RecyclerViewTopics))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }


    @After
    public void tearDown() throws Exception {
        topicsActivity = null;
    }
}