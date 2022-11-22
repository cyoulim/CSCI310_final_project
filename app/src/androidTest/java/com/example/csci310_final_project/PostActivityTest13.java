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
public class PostActivityTest13 {
    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void Test_Post_clear_form(){
        onView(withId(R.id.username))
                .perform(typeText("judy@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.bio))
                .perform(typeText("cs"), closeSoftKeyboard());
        onView(withId(R.id.deadline))
                .perform(typeText("today"), closeSoftKeyboard());
        onView(withId(R.id.address))
                .perform(typeText("home"), closeSoftKeyboard());
        onView(withId(R.id.rent))
                .perform(typeText("1000"), closeSoftKeyboard());
        onView(withId(R.id.utilities))
                .perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.buttonPost)).perform(click());

        onView(withId(R.id.username)).check(matches(withText("")));
        onView(withId(R.id.bio)).check(matches(withText("")));
        onView(withId(R.id.deadline)).check(matches(withText("")));
        onView(withId(R.id.address)).check(matches(withText("")));
        onView(withId(R.id.rent)).check(matches(withText("")));
        onView(withId(R.id.utilities)).check(matches(withText("")));
    }
}

