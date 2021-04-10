package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.provider.RealEstateManagerProvider
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class ContentProviderTest {

    private var mContentResolver: ContentResolver? = null

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                RealEstateDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mContentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun itemsWhenNoItemInserted() {
            val cursor: Cursor? = mContentResolver!!.query(ContentUris.withAppendedId(RealEstateManagerProvider.URI_ITEM, USER_ID), null, null, null, null)
            ViewMatchers.assertThat(cursor, Matchers.notNullValue())
            ViewMatchers.assertThat(cursor?.count, Matchers.`is`(0))
            cursor?.close()
        }

    @Test
    fun insertAndGetItem() {
        // BEFORE : Adding demo item
        mContentResolver!!.insert(RealEstateManagerProvider.URI_ITEM, generateItem())
        // TEST
        val cursor: Cursor? = mContentResolver!!.query(ContentUris.withAppendedId(RealEstateManagerProvider.URI_ITEM, USER_ID), null, null, null, null)
        ViewMatchers.assertThat(cursor, Matchers.notNullValue())
        ViewMatchers.assertThat(cursor?.count, Matchers.`is`(1))
        ViewMatchers.assertThat(cursor?.moveToFirst(), Matchers.`is`(true))
        ViewMatchers.assertThat(cursor?.getString(cursor.getColumnIndexOrThrow("type")), Matchers.`is`("House"))
    }

    // ---
    private fun generateItem(): ContentValues {
        val values = ContentValues()
        values.put("id", 1)
        values.put("type", "Flat")
        return values
    }

    companion object {
        // DATA SET FOR TEST
        private const val USER_ID: Long = 1
    }
}