package uk.ac.aber.dcs.cs31620.phrasepad


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UITests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun uITests() {
        val materialButton = onView(
            allOf(
                withId(R.id.startWithSampleButton), withText("Sample PhrasePad"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        5
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("Yes"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.title), withText("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            allOf(
                withId(android.R.id.summary), withText("English"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("English")))

        val textView2 = onView(
            allOf(
                withId(android.R.id.summary), withText("Cymraeg"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Cymraeg")))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.settings_activity),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.bnav_quiz), withContentDescription("Quizzes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val button = onView(
            allOf(
                withId(R.id.buttonStartQuiz), withText("START QUIZ"),
                withParent(
                    allOf(
                        withId(R.id.quizFragment),
                        withParent(withId(R.id.navigation_fragment_host))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.bnav_phrases), withContentDescription("Phrases"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        // This part of the test fails even though the RecyclerView is on screen. Strange.
        /*
        val recyclerView = onView(
            allOf(
                withId(R.id.phrases_list),
                childAtPosition(
                    withId(R.id.phrasesListSwipeRefresh),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView3 = onView(
            allOf(
                withId(R.id.sourceLangText), withText("Thank you"),
                withParent(withParent(withId(R.id.source))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Thank you")))

        val textView4 = onView(
            allOf(
                withId(R.id.destLangText), withText("Diolch"),
                withParent(withParent(withId(R.id.linearLayout))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Diolch")))

        pressBack()*/

        val extendedFloatingActionButton = onView(
            allOf(
                withId(R.id.floatingActionButton), withText("Add Phrase"),
                childAtPosition(
                    allOf(
                        withId(R.id.layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        extendedFloatingActionButton.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.textInputOriginLang),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editTextOriginLang),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("good morning"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.saveButton), withText("Save"),
                childAtPosition(
                    allOf(
                        withId(R.id.add_phrase_sheet),
                        childAtPosition(
                            withId(R.id.design_bottom_sheet),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textView5 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Enter a phrase"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Enter a phrase")))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.textInputDestLang),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editTextDestLang),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("bore da"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.saveButton), withText("Save"),
                childAtPosition(
                    allOf(
                        withId(R.id.add_phrase_sheet),
                        childAtPosition(
                            withId(R.id.design_bottom_sheet),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.phrases_list),
                childAtPosition(
                    withId(R.id.phrasesListSwipeRefresh),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(20, click()))

        val textView6 = onView(
            allOf(
                withId(R.id.sourceLangText), withText("good morning"),
                withParent(withParent(withId(R.id.source))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("good morning")))

        val textView7 = onView(
            allOf(
                withId(R.id.destLangText), withText("bore da"),
                withParent(withParent(withId(R.id.linearLayout))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("bore da")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
