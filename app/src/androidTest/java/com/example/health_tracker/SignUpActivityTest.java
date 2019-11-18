package com.example.health_tracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule =
            new ActivityTestRule<SignUpActivity>(SignUpActivity.class);
    public SignUpActivity signUpActivity = null;

    private String email = "check@gmail.com";
    private String name = "check";
    private String weight = "50";
    private String height = "170";

    @Before
    public void setUp() throws Exception {
        signUpActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void UiLoading() {
        //ui loading
        assertNotNull(signUpActivity);

    }

    @Test
    public void testSetup() throws IOException {

        onView(withId(R.id.signup_email)).perform(replaceText(email));
        onView(withId(R.id.signup_Name)).perform(replaceText(name));
        onView(withId(R.id.signup_password)).perform(replaceText("1234567"));
        onView(withId(R.id.weight)).perform(replaceText(weight));
        onView(withId(R.id.height)).perform(replaceText(height));
//        onView(withId(R.id.signup_button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        signUpActivity = null;
    }
}