package com.example.health_tracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;

public class WaterIntakeActivityTest {


    @Rule
    public ActivityTestRule<WaterIntakeActivity> activityTestRule = new
            ActivityTestRule<WaterIntakeActivity>(WaterIntakeActivity.class);
    WaterIntakeActivity activity = null;


    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void UILoad() {
        assertNotNull(activity);
    }

    @Test
    public void ClickEvent() {

        onView(withId(R.id.saveBodyWater)).perform(click());
        onView(withText("Successfully Saved")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

}