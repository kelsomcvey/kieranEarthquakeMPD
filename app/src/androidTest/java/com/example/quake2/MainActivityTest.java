package com.example.quake2;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> myActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    private MainActivity myActivity = null;
    @Before
    public void setUp() throws Exception {
        myActivity = myActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        myActivity = null;
    }

    @Test
    public void onCreate() {
        View view = myActivity.findViewById(R.id.run_button);
        assertNotNull(view);
    }

    @Test
    public void testRunButton ()
    {
        assertNotNull(myActivity.findViewById(R.id.run_button));
        onView(withId(R.id.run_button)).perform(click());

        getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
    }


}