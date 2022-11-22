package com.example.csci310_final_project;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisterActivitytest01 {
    @Rule public ActivityScenarioRule<RegisterActivity> activityScenarioRule
            = new ActivityScenarioRule<>(RegisterActivity.class);
    @Test
    public void Test_Email_Already_Registered() {
        onView(withId(R.id.email))
                .perform(typeText("hwinnie@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("test123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonSignup)).perform(click());

        onView(withId(R.id.errorMsg))
                .check(matches(withText(containsString("existing profile with email"))));
    }

}
