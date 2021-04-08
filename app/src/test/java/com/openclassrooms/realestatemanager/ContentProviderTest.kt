package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.provider.RealEstateManagerProvider
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ContentProviderTest {

    private var mContentResolver: ContentResolver? = null

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mContentResolver = InstrumentationRegistry.getContext().contentResolver
    }

    @get:Test
    val itemsWhenNoItemInserted: Unit
        get() {
            val cursor: Cursor? = mContentResolver!!.query(ContentUris.withAppendedId(RealEstateManagerProvider.URI_ITEM, USER_ID), null, null, null, null)
            ViewMatchers.assertThat(cursor, Matchers.notNullValue())
            ViewMatchers.assertThat(cursor?.count, Matchers.`is`(0))
            cursor?.close()
        }

    @Test
    fun insertAndGetItem() {
        // BEFORE : Adding demo item
        val userUri: Uri? = mContentResolver!!.insert(RealEstateManagerProvider.URI_ITEM, generateItem())
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