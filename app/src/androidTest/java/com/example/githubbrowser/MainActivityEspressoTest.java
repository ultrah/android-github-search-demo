package com.example.githubbrowser;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.githubbrowser.ui.MainActivity;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Ignore
    @Test
    // TODO Stuff happens in the background -> Either inject mock ViewModel or implement IdlingResource
    public void shouldDisplayResults() {
        // Given a search query
        onView(withId(R.id.edit_keywords)).perform(typeText("Foobar"));

        // When a search is started
        onView(withId(R.id.btn_search)).perform(click());

        // Then the result should be displayed
        onView(withId(R.id.container_recycler)).check(matches(isDisplayed()));
    }
}
