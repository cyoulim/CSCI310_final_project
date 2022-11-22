package com.example.csci310_final_project;
import android.app.Activity;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
public class LoginActivityTest09 {
    @Rule public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);
    @Test
    public void Test_Login_already_registered(){
        onView(withId(R.id.email))
                .perform(typeText("judy@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.buttonLogin)).perform(click());

        onView(withId(R.id.errorMsg)).check(matches(withText("")));
    }
}
