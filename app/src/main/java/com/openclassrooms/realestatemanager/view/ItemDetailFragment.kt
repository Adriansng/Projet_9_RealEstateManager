package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : androidx.fragment.app.Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: RealEstate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = it.getParcelable(ARG_ITEM_ID)
                activity?.findViewById<Toolbar>(R.id.item_detail_toolbar)?.title = item?.type
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.findViewById<TextView>(R.id.item_detail_type_txt)
            rootView.findViewById<TextView>(R.id.item_detail_description_txt)
            rootView.findViewById<TextView>(R.id.item_detail_surface_txt)
            // room
            rootView.findViewById<TextView>(R.id.item_detail_room_txt)
            rootView.findViewById<TextView>(R.id.item_detail_bedroom_txt)
            rootView.findViewById<TextView>(R.id.item_detail_bathroom_txt)
            // address
            rootView.findViewById<TextView>(R.id.item_detail_address_txt)
            rootView.findViewById<TextView>(R.id.item_detail_city_txt)
            rootView.findViewById<TextView>(R.id.item_detail_code_postal_txt)
            // close
            rootView.findViewById<ImageView>(R.id.item_detail_school_check_iv)
            rootView.findViewById<ImageView>(R.id.item_detail_commerce_check_iv)
            rootView.findViewById<ImageView>(R.id.item_detail_park_check_iv)
            // map
            rootView.findViewById<ImageView>(R.id.item_detail_map_location_iv)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}