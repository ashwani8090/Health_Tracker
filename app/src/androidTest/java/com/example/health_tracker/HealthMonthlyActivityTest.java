package com.example.health_tracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class HealthMonthlyActivityTest {

    @Rule
    public ActivityTestRule<HealthMonthlyActivity> activityTestRule = new
            ActivityTestRule<HealthMonthlyActivity>(HealthMonthlyActivity.class);
    public HealthMonthlyActivity monthlyActivity = null;

    @Before
    public void setUp() throws Exception {
        monthlyActivity = activityTestRule.getActivity();
    }

    @Test
    public void onClickEvent() {

        onView(withId(R.id.bpMonth)).perform(click());
        onView(withId(R.id.tempMonth)).perform(click());
        onView(withId(R.id.sugarMonth)).perform(click());
        onView(withId(R.id.waterMonth)).perform(click());

    }

    @Test
    public void UiLoading() {
        //ui loading
        assertNotNull(monthlyActivity);

    }

    @After
    public void tearDown() throws Exception {
        monthlyActivity = null;
    }
}