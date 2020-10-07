package br.com.ricardo.gistsgithub

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.ricardo.gistsgithub.presentation.gistslist.ui.GistsListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GistsListActivityTest {

    @get:Rule
    val rule = ActivityTestRule<GistsListActivity>(GistsListActivity::class.java)

    val LIST_ITEM_IN_TEST = 4
    val GISTS_IN_TEST = FakeGistsList.gist[LIST_ITEM_IN_TEST]

    @Test
    fun gists_view_flipper_visible_with_progressbar_on_app_launch() {
        Espresso.onView(withId(R.id.gists_view_flipper))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun item_git_swipup() {
        Espresso.onView(withId(R.id.gists_view_flipper))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val maxAttempts = 3
        Espresso.onView(withId(R.id.gists_view_flipper))
            .perform(
                ViewActions.repeatedlyUntil(
                    ViewActions.swipeUp(),
                    ViewMatchers.hasDescendant(withText(GISTS_IN_TEST.owner.login)),
                    maxAttempts
                ),
                ViewActions.click()
            )
    }

    @Test
    fun item_git_swipeup_and_back() {
        Espresso.onView(withId(R.id.gists_view_flipper))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val maxAttempts = 2
        Espresso.onView(withId(R.id.gists_view_flipper))
            .perform(
                ViewActions.repeatedlyUntil(
                    ViewActions.swipeUp(),
                    ViewMatchers.hasDescendant(withText(GISTS_IN_TEST.owner.login)),
                    maxAttempts
                ),
                ViewActions.click()
            )

        ViewActions.pressBack()
    }

}