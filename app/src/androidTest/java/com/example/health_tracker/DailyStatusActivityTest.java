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

public class DailyStatusActivityTest {

    @Rule
    public ActivityTestRule<DailyStatusActivity> mActivityTestRule =
            new ActivityTestRule<DailyStatusActivity>(DailyStatusActivity.class);
    public DailyStatusActivity dailyStatusActivity = null;

    @Before
    public void setUp() throws Exception {
        dailyStatusActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void UiLoading() {
        //ui loading
        assertNotNull(dailyStatusActivity);

    }

    @Test
    public void onClickEvent() {

        onView(withId(R.id.bpDaily)).perform(click());
        onView(withId(R.id.tempDaily)).perform(click());
        onView(withId(R.id.sugarDaily)).perform(click());
        onView(withId(R.id.waterDaily)).perform(click());


    }

    @After
    public void tearDown() throws Exception {
        dailyStatusActivity = null;
    }


}