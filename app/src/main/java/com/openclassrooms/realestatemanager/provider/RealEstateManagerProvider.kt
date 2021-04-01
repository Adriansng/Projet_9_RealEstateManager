package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.model.RealEstate
import kotlinx.coroutines.runBlocking

class RealEstateManagerProvider : ContentProvider() {

    // FOR DATA
    private val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
    private val TABLE_NAME = RealEstate::class.java.simpleName

    override fun onCreate() = true

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        context?.let { context ->
            contentValues?.let {
                var contentUris: Uri? = null
                runBlocking {
                    val realEstate = RealEstate.fromContentValues(it)
                    val id = RealEstateDatabase.buildDatabase(context)?.realEstateDao()?.insertRealEstates(realEstate)
                    if (id != 0L) {
                        context.contentResolver.notifyChange(uri, null)
                        contentUris = ContentUris.withAppendedId(uri, id!!)
                    }
                }
                return contentUris
            }
        }

        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {

        throw IllegalArgumentException("You can't delete anything")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        context?.let { context ->
            contentValues?.let {
                var count = 0
                runBlocking {
                    count = RealEstateDatabase.buildDatabase(context)?.realEstateDao()?.update(RealEstate.fromContentValues(it))!!
                }
                context.contentResolver.notifyChange(uri, null)
                return count
            }
        }

        throw IllegalArgumentException("Failed to update row into $uri")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        context?.let { context ->
            val id = ContentUris.parseId(uri).toInt()
            val cursor = RealEstateDatabase.buildDatabase(context)!!.realEstateDao().getRealEstateCursor(id)
            cursor!!.setNotificationUri(context.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? =
    "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
}


