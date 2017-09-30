package net.artgamestudio.rgatest.ui.activities;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.artgamestudio.rgatest.R;
import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.data.rn.ContactRN;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EditContactActivityEspressoTest {

    @Mock
    ContactRN mContactRN;

    /***** CONSTANTS *****/
    private final int CONTACT_ID = 1;
    private final String CONTACT_NAME = "Ailton Ramos";
    private final String CONTACT_EMAIL = "ailton.tk@hotmail.com";
    private final String CONTACT_BORN = "27/03/1994";
    private final String CONTACT_BIO = "Desenvolvedor Android";

    @Rule
    public ActivityTestRule<EditContactActivity> mActivityRule =
            new ActivityTestRule<>(EditContactActivity.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Contact contact = new Contact(CONTACT_ID, CONTACT_NAME, CONTACT_EMAIL, CONTACT_BORN, CONTACT_BIO);

        mContactRN = spy(new ContactRN(null, null));
        doReturn(contact).when(mContactRN).getContact(CONTACT_ID);
    }

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.etName)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.etBorn)).check(matches(isDisplayed()));
        onView(withId(R.id.etBio)).check(matches(isDisplayed()));
    }

    @Test
    public void whenActivityIsRunning_shouldDisplayContactInfo() throws Exception {
        Contact contact = mContactRN.getContact(CONTACT_ID);

        onView(withId(R.id.etName)).perform(ViewActions.typeText(contact.getName()));
        closeSoftKeyboard();

        onView(withId(R.id.etEmail)).perform(ViewActions.typeText(contact.getEmail()));
        closeSoftKeyboard();

        onView(withId(R.id.etBio)).perform(ViewActions.typeText(contact.getBio()));
        closeSoftKeyboard();

        onView(withId(R.id.etName)).check(matches(withText(CONTACT_NAME)));
        onView(withId(R.id.etEmail)).check(matches(withText(CONTACT_EMAIL)));
        onView(withId(R.id.etBio)).check(matches(withText(CONTACT_BIO)));
    }
}
