package com.example.health_tracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class DashBoardActivityTest {


    @Rule
    public ActivityTestRule<DashBoardActivity> activityTestRule = new
            ActivityTestRule<DashBoardActivity>(DashBoardActivity.class);
    public DashBoardActivity dashBoardActivity = null;


    @Before
    public void setUp() throws Exception {

        dashBoardActivity = activityTestRule.getActivity();
    }


    @Test
    public void UiLoading() {

        //multiple operation together
        //onView(AllOf.allOf(withId(R.id.editTextName),withId(R.id.editTextSameName))).
        // check(matches(withText("Pavneet")));

        assertNotNull(dashBoardActivity);

    }

    @Test
    public void RecyclerTest() {
        // verify the visibility of recycler view on screen
        onView(withId(R.id.RecyclerView)).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        dashBoardActivity = null;
    }

    @Test
    public void validate() {
    }
}