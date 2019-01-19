package org.codelab.google.bakingapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void frag(){
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testing() {
        onView(withId(R.id.recyclerview)).check(matches((isDisplayed())));
    }


}
