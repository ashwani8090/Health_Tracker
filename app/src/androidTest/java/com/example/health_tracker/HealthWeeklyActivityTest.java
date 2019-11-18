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

public class HealthWeeklyActivityTest {


    @Rule
    public ActivityTestRule<HealthWeeklyActivity> activityTestRule = new
            ActivityTestRule<HealthWeeklyActivity>(HealthWeeklyActivity.class);
    public HealthWeeklyActivity weeklyActivity = null;

    @Before
    public void setUp() throws Exception {
        weeklyActivity = activityTestRule.getActivity();
    }

    @Test
    public void onClickEvent() {

        onView(withId(R.id.bpWeek)).perform(click());
        onView(withId(R.id.tempWeek)).perform(click());
        onView(withId(R.id.sugarWeek)).perform(click());
        onView(withId(R.id.waterWeek)).perform(click());

    }

    @Test
    public void UiLoading() {
        //ui loading
        assertNotNull(weeklyActivity);

    }

    @After
    public void tearDown() throws Exception {
        weeklyActivity = null;
    }
}