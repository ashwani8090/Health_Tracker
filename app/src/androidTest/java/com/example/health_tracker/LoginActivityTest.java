package com.example.health_tracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class LoginActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private String user = "ashwani8090singh@gmail.com";
    private String password = "1234567";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void UiLoading() {
        //ui loading
        assertNotNull(mActivityTestRule.getActivity());

    }

    @Test
    public void testSetup() throws IOException {

        onView(withId(R.id.login_email)).perform(replaceText(user));
        onView(withId(R.id.login_password)).perform(replaceText(password));
        onView(withId(R.id.buttonLogin)).perform(click());
    }


    @After
    public void tearDown() throws Exception {
    }
}