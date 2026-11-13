package com.example.calculadoracientifica

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.calculadoracientifica", appContext.packageName)
    }

    @Test
    fun portraitLayout_displaysBasicButtonsAndCalculates() {
        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
        onView(withId(R.id.btnClear)).perform(click())
        onView(withId(R.id.btn2)).perform(click())
        onView(withId(R.id.btnAdd)).perform(click())
        onView(withId(R.id.btn3)).perform(click())
        onView(withId(R.id.btnEqual)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(withText("5")))
    }

    @Test
    fun landscapeLayout_displaysScientificButtonsAndCalculates() {
        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onView(withId(R.id.btnSin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCos)).check(matches(isDisplayed()))
        onView(withId(R.id.btnPi)).check(matches(isDisplayed()))
        onView(withId(R.id.btnClear)).perform(click())
        onView(withId(R.id.btnSqrt)).perform(click())
        onView(withId(R.id.btn9)).perform(click())
        onView(withId(R.id.btnCloseParen)).perform(click())
        onView(withId(R.id.btnEqual)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(withText("3")))
    }
}
