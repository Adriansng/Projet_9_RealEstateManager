package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.R.drawable
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
                item = it.getSerializable(ARG_ITEM_ID) as RealEstate?
                activity?.findViewById<Toolbar>(R.id.item_detail_toolbar)?.title = item?.type
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            item!!.type.also { rootView.findViewById<TextView>(R.id.item_detail_type_txt).text = it }
            item!!.descriptionRealEstate.also { rootView.findViewById<TextView>(R.id.item_detail_description_txt).text = it }
            (item!!.area.toString()+" m²").also { rootView.findViewById<TextView>(R.id.item_detail_surface_txt).text = it }
            // room
            item!!.numberRoom.toString().also { rootView.findViewById<TextView>(R.id.item_detail_room_txt).text = it }
            item!!.numberBedroom.toString().also { rootView.findViewById<TextView>(R.id.item_detail_bedroom_txt).text = it }
            item!!.numberBathroom.toString().also { rootView.findViewById<TextView>(R.id.item_detail_bathroom_txt).text = it }
            // address
            item!!.address.also { rootView.findViewById<TextView>(R.id.item_detail_address_txt).text = it }
            item!!.city.also { rootView.findViewById<TextView>(R.id.item_detail_city_txt).text = it }
            item!!.zipCode.also { rootView.findViewById<TextView>(R.id.item_detail_code_postal_txt). text = it }
            // close
            if(!item!!.closeToSchool){
            rootView.findViewById<ImageView>(R.id.item_detail_school_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_clear_24))
            }
            if(!item!!.closeToCommerce){
            rootView.findViewById<ImageView>(R.id.item_detail_commerce_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_clear_24))
            }
            if(!item!!.closeToPark){
                rootView.findViewById<ImageView>(R.id.item_detail_park_check_iv)
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_clear_24))
            }
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