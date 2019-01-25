package org.codelab.google.bakingapp;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class RecipeFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> fragmentTestRule =
            new ActivityTestRule<>(MainActivity.class);
    //Looks for the recycler view with id "recyclerview" to confirm fragment is loaded
    @Test
    public void confirmLayout() {
        onView(withId(R.id.recyclerview))
                .check(matches(withResourceName("recyclerview")));
    }
    @Test
    public void navigateToRecipeIntro() {
        //checks the recyclerview and clicks on the "Brownies" recipe
        onView(withId(R.id.recyclerview))
                .check(RecyclerViewAssertions.recyclerViewItemWith(withText("Brownies")))
                .perform(click());
        //verifies that the ingredients view is showing
        onView(withId(R.id.ingredients))
                .check(matches(withResourceName("ingredients")));
        //verifies that the list of steps view is showing and clicks on introduction
        onView(withId(R.id.recyclerview))
                .check(RecyclerViewAssertions.recyclerViewItemWith(withText("Recipe Introduction")))
                .perform(click());
        //confirms view for video is shown
        onView(withId(R.id.video_view))
                .check(matches(withResourceName("video_view")));
    }
}
