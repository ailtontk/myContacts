package net.artgamestudio.rgatest.ui.activities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.artgamestudio.rgatest.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureSearch() {
        //Click on the first item of recycler view
        onView(withId(R.id.rvContacts))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if fiels were correctly positioned
        onView(withId(R.id.tvEmail)).check(matches(withText("Name Person1")));
    }
}
