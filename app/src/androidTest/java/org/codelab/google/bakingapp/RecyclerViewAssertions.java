package org.codelab.google.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class RecyclerViewAssertions {
    /**
        from resource https://medium.com/koala-tea-assurance/better-android-recyclerview-testing-a5e3ed7ef38b
     */
    static ViewAssertion recyclerViewItemWith(final Matcher<View> viewMatcher) {
        assertNotNull(viewMatcher);
        return new ViewAssertion() {

            @Override
            public void check(View view, NoMatchingViewException noMatchingViewException) {
                if (noMatchingViewException != null) {
                    throw noMatchingViewException;
                }
                assertTrue(view instanceof RecyclerView);
                RecyclerView recyclerView = (RecyclerView) view;
                final RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter != null) {
                    for (int position = 0; position < adapter.getItemCount(); position++) {
                        int itemType = adapter.getItemViewType(position);
                        RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(recyclerView, itemType);
                        adapter.bindViewHolder(viewHolder, position);

                        if (viewHolderMatcher(hasDescendant(viewMatcher)).matches(viewHolder)) {
                            return;
                        }
                    }
                }
                fail("No match found");
            }
        };
    }
    private static Matcher<RecyclerView.ViewHolder> viewHolderMatcher(final Matcher<View> itemViewMatcher) {
        return new TypeSafeMatcher<RecyclerView.ViewHolder>() {
            @Override
            public boolean matchesSafely(RecyclerView.ViewHolder viewHolder) {
                return itemViewMatcher.matches(viewHolder.itemView);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("holder with view: ");
                itemViewMatcher.describeTo(description);
            }
        };
    }
}