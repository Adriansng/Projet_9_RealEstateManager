package com.openclassrooms.realestatemanager.view.itemDetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.view.itemCreation.ItemCreationRealEstateActivity
import com.openclassrooms.realestatemanager.view.itemList.ItemListActivity

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ItemDetailActivity : AppCompatActivity() {


    private lateinit var realEstateId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.item_detail_toolbar))

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        realEstateId = intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID).toString()

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            val fragment = ItemDetailFragment().apply {
               arguments = Bundle().apply {
                   putString(ItemDetailFragment.ARG_ITEM_ID,
                           intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID))
               }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    // ------------------
    // UI
    // ------------------

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_detail_activity, menu)
        return true
    }

    // ------------------
    // ACTIVITY
    // ------------------


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    intent = Intent(this, ItemListActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.item_list_edit_toolbar -> {
                    launchItemCreation()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
    }

    private fun launchItemCreation(){
        val intent = Intent(this, ItemCreationRealEstateActivity::class.java)
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, realEstateId)
        startActivity(intent)
    }

}