package com.openclassrooms.realestatemanager.view.itemList


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class ItemListActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ItemListActivity::class.java)

    @Test
    fun itemListActivityTest() {
        val actionMenuItemView = onView(
                allOf(withId(R.id.item_list_add_toolbar), withContentDescription("Ajouter un bien"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item_list_toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        val viewGroup = onView(
                allOf(withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()))
        viewGroup.check(matches(isDisplayed()))

        val appCompatImageButton = onView(
                allOf(withContentDescription("Revenir en haut de la page"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(R.id.item_list_toolbar),
                                        childAtPosition(
                                                withId(R.id.item_list_activity),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton2.perform(click())

        val checkedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Simulateur de Prêt"),
                        withParent(allOf(withId(R.id.menu_loan_item),
                                withParent(withId(R.id.design_navigation_view)))),
                        isDisplayed()))
        checkedTextView.check(matches(isDisplayed()))

        val linearLayoutCompat = onView(
                allOf(withId(R.id.menu_map_item),
                        withParent(allOf(withId(R.id.design_navigation_view),
                                withParent(withId(R.id.nav_view_menu)))),
                        isDisplayed()))
        linearLayoutCompat.check(matches(isDisplayed()))

        val checkedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Changer de devices"),
                        withParent(allOf(withId(R.id.menu_change_device_item),
                                withParent(withId(R.id.design_navigation_view)))),
                        isDisplayed()))
        checkedTextView2.check(matches(isDisplayed()))

        val checkedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Ajouter un agent"),
                        withParent(allOf(withId(R.id.menu_add_realtor_item),
                                withParent(withId(R.id.design_navigation_view)))),
                        isDisplayed()))
        checkedTextView3.check(matches(isDisplayed()))

        val recyclerView = onView(
                allOf(withId(R.id.design_navigation_view),
                        withParent(allOf(withId(R.id.nav_view_menu),
                                withParent(withId(R.id.drawer_layout)))),
                        isDisplayed()))
        recyclerView.check(matches(isDisplayed()))


        val navigationMenuItemView = onView(
                allOf(withId(R.id.menu_add_realtor_item),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view_menu),
                                                0)),
                                1),
                        isDisplayed()))
        navigationMenuItemView.perform(click())

        val frameLayout = onView(
                allOf(withId(android.R.id.content),
                        withParent(allOf(withId(R.id.action_bar_root),
                                withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java)))),
                        isDisplayed()))
        frameLayout.check(matches(isDisplayed()))

        val materialButton = onView(
                allOf(withId(android.R.id.button3), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                0)))
        materialButton.perform(scrollTo(), click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int,
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
