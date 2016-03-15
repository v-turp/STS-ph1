package com.score.sts;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Who Dat on 2/25/2016.
 */

@RunWith(AndroidJUnit4.class)
public class LandingPageTest {

    @Rule public final ActivityRule<LandingPage> landingPageActivityRule = new ActivityRule<>(LandingPage.class);

    @Test
    public void checkForLaunchButtonText(){
        onView(withText("LOGIN")).check(ViewAssertions.matches(isDisplayed()));
    }
}
