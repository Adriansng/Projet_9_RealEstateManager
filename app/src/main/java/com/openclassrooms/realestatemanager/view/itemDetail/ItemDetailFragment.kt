package com.openclassrooms.realestatemanager.view.itemDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewModel.ItemDetailFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ItemDetailFragment : androidx.fragment.app.Fragment() {

    // --- FOR DATA ---

    private val viewModel : ItemDetailFragmentViewModel by viewModel()
    private var item: RealEstate? = null

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = setUpRealEstates(it.getString(ARG_ITEM_ID))
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
            item!!.price.also { rootView.findViewById<TextView>(R.id.item_detail_price_txt).text = it.toString() }
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
            }else{
                rootView.findViewById<ImageView>(R.id.item_detail_school_check_iv)
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_check_24))
            }
            if(!item!!.closeToCommerce){
            rootView.findViewById<ImageView>(R.id.item_detail_commerce_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_clear_24))
            }else{
                rootView.findViewById<ImageView>(R.id.item_detail_commerce_check_iv)
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_check_24))
            }
            if(!item!!.closeToPark){
                rootView.findViewById<ImageView>(R.id.item_detail_park_check_iv)
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_clear_24))
            }else{
                rootView.findViewById<ImageView>(R.id.item_detail_park_check_iv)
                        .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_check_24))
            }
            // map
            //rootView.findViewById<ImageView>(R.id.item_detail_map_location_iv)
            // date
            item!!.creationDate.also { rootView.findViewById<TextView>(R.id.item_detail_date_creation_txt). text = Utils.getFormatDate(it) }
            if(item!!.saleCreation != null){
                item!!.saleCreation.also { rootView.findViewById<TextView>(R.id.item_detail_date_sold_txt). text = Utils.getFormatDate(it) }
                VISIBLE.also { rootView.findViewById<ImageView>(R.id.item_detail_sale_iv).visibility = it }
            }else{
                "".also { rootView.findViewById<TextView>(R.id.item_detail_date_sold_txt). text = it }
                "".also { rootView.findViewById<TextView>(R.id.item_detail_date_sold_title_txt).text = it }
                rootView.findViewById<ImageView>(R.id.item_detail_sale_iv).visibility = INVISIBLE
            }
            // realtor
            //item!!.idRealtor.also { rootView.findViewById<TextView>(R.id.item_detail_realtor_creation_txt). text = setUpRealtor(it) }


        }

        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    // ------------------
    // REAL ESTATE
    // ------------------

    private fun setUpRealEstates(id: String?): RealEstate {
        return viewModel.getRealEstate(id.toString().toLong())
    }

    // ------------------
    // REALTOR
    // ------------------

    private fun setUpRealtor(id: Long): String {
        return viewModel.getRealtor(id).name
    }

}