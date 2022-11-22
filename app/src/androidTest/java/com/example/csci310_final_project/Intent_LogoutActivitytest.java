package com.example.csci310_final_project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Intent_LogoutActivitytest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception{
        Intents.init();
    }
    @Test
    public void Test_Intent_to_Register() {
        onView(withId(R.id.logout_Button)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }
    @After
    public void tearDown() throws Exception{
        Intents.release();
    }
}
