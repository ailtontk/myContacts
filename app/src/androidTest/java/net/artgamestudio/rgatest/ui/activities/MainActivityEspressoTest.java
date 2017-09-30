package net.artgamestudio.rgatest.ui.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.data.rn.ContactRN;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Mock
    ContactRN mContactRN;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.tvMainLogo)).check(matches(isDisplayed()));
        onView(withId(R.id.fbAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.rvContacts)).check(matches(isDisplayed()));
    }
}
