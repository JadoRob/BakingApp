package org.codelab.google.bakingapp;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RecipeFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> fragmentTestRule =
            new ActivityTestRule<>(MainActivity.class);

    //Looks for the recycler view with id "recyclerview" to confirm fragment is loaded
    @Test
    public void confirmLayout() {
        onView(withId(R.id.recyclerview))
                .check(matches(ViewMatchers.withResourceName("recyclerview")));
    }

    @Test
    public void clickOnRecipeItem() {
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));

    }

//    @Test
//    public void checkIngredientList() {
//        onView(withId(R.id.ingredients))
//                .check(matches(ViewMatchers.withResourceName("ingredients")));
//    }
}
