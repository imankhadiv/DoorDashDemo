package elrast.com.doordashapp.activity;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import elrast.com.doordashapp.R;
import elrast.com.doordashapp.model.Restaurant;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldDisplayRecyclerView() {
        onView(withId(R.id.restaurantRecyclerView)).check(matches((isDisplayed())));
    }

    @Test
    public void shouldDisplayDrawerLayout() {
        onView(withId(R.id.drawer_layout)).check(matches((isDisplayed())));
    }

    @Test
    public void shouldDisplayAppBarLayout() {
        onView(withId(R.id.app_bar_main)).check(matches((isDisplayed())));
    }

    @Test
    public void shouldDisplayMainContent() {
        onView(withId(R.id.content_main)).check(matches((isDisplayed())));
    }

    @Test
    public void testToolbarTitleText_shouldHaveCorrectText() {

        onView(withText(R.string.discover)).check(matches(withParent(withId(R.id.toolbar))));
    }

    @Test
    public void testRestaurantRecyclerViewItem_shouldHaveRestaurantData() {
        Restaurant restaurant = new Restaurant("0",
                "Starbucks", "Coffee & Tea, Breakfast & Brunch",
                "", "pre-order1"
        );
        onView(withId(R.id.restaurantRecyclerView)).check(matches(hasRestaurantDataWithPosition(0, restaurant)));
    }

    private Matcher<View> hasRestaurantDataWithPosition(final int position, @NonNull final Restaurant restaurant) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("No!!! Item has restaurant data at position" + position);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {

                if (recyclerView == null) {
                    return false;
                }
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                View targetView = viewHolder.itemView;
                return hasDescendant(withChild(withText(restaurant.getName()))).matches(targetView);
            }
        };
    }
}