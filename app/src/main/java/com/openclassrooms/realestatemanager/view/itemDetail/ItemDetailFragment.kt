package com.openclassrooms.realestatemanager.view.itemDetail

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.RealEstateComplete
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewModel.ItemDetailFragmentViewModel
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.util.*

class ItemDetailFragment : androidx.fragment.app.Fragment() {

    // --- FOR DATA ---

    private val viewModel : ItemDetailFragmentViewModel by viewModel()
    private var itemId: Long? = null
    private lateinit var recyclerView : RecyclerView

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
                itemId = it.getLong(ARG_ITEM_ID)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        // Show the dummy content as text in a TextView.
        itemId?.let { setUpRealEstates(it, rootView) }
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
    // ------------------
    // PHOTO
    // ------------------

    private fun setUpRecyclerView(listPhoto: List<Photo>) {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ItemDetailRecyclerViewAdapter(listPhoto)
    }

    // ------------------
    // REAL ESTATE
    // ------------------

    private fun setUpRealEstates(id: Long, rootView: View) {
         viewModel.getRealEstate(id).observe(viewLifecycleOwner, {
             initRealEstate(rootView, it)
         })
    }

    @SuppressLint("SetTextI18n")
    private fun initRealEstate(rootView: View, it: RealEstateComplete) {
        activity?.findViewById<Toolbar>(R.id.item_detail_toolbar)?.title = it.realEstate.type
        getCurrentRealtor(it.realEstate, rootView.findViewById(R.id.item_detail_price_txt))
        rootView.findViewById<TextView>(R.id.item_detail_type_txt).text = it.realEstate.type
        rootView.findViewById<TextView>(R.id.item_detail_description_txt).text = it.realEstate.descriptionRealEstate
        rootView.findViewById<TextView>(R.id.item_detail_surface_txt).text = it.realEstate.area.toString() + " m²"
        // room
        rootView.findViewById<TextView>(R.id.item_detail_room_txt).text = it.realEstate.numberRoom.toString()
        rootView.findViewById<TextView>(R.id.item_detail_bedroom_txt).text = it.realEstate.numberBedroom.toString()
        rootView.findViewById<TextView>(R.id.item_detail_bathroom_txt).text = it.realEstate.numberBathroom.toString()
        // address
        rootView.findViewById<TextView>(R.id.item_detail_address_txt).text = it.realEstate.address
        rootView.findViewById<TextView>(R.id.item_detail_city_txt).text = it.realEstate.city
        rootView.findViewById<TextView>(R.id.item_detail_code_postal_txt).text = it.realEstate.zipCode
        // close
        if (!it.realEstate.closeToSchool) {
            rootView.findViewById<ImageView>(R.id.item_detail_school_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_clear_24))
        } else {
            rootView.findViewById<ImageView>(R.id.item_detail_school_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_24))
        }
        if (!it.realEstate.closeToCommerce) {
            rootView.findViewById<ImageView>(R.id.item_detail_commerce_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_clear_24))
        } else {
            rootView.findViewById<ImageView>(R.id.item_detail_commerce_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_24))
        }
        if (!it.realEstate.closeToPark) {
            rootView.findViewById<ImageView>(R.id.item_detail_park_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_clear_24))
        } else {
            rootView.findViewById<ImageView>(R.id.item_detail_park_check_iv)
                    .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_24))
        }
        // map
        if (Utils.isInternetAvailable(requireContext())) {
            if (it.realEstate.location == null) {
                val geoCoder = Geocoder(requireContext(), Locale.getDefault())
                try {
                    val addresses: MutableList<Address> = geoCoder.getFromLocationName(it.realEstate.address + " "
                            + it.realEstate.city + " "
                            + it.realEstate.zipCode,
                            1)
                    val location: Address = addresses[0]
                    it.realEstate.location = LatLng(location.latitude, location.longitude)
                    viewModel.addRealEstate(it.realEstate)
                } catch (e: Exception) {
                    print(e.message)
                    Toast.makeText(requireContext(), getString(R.string.add_not_address_valide), Toast.LENGTH_SHORT).show()
                }
            }
            val mapView: ImageView = rootView.findViewById(R.id.item_detail_map_location_iv)
            if (it.realEstate.location != null) run {
                val lat: Double = it.realEstate.location!!.latitude
                val lon: Double = it.realEstate.location!!.longitude
                var url = "https://maps.googleapis.com/maps/api/staticmap?"
                url += "&zoom=16"
                url += "&size=125x125"
                url += "&maptype=roadmap"
                url += "&markers=color:green%7Clabel:G%7C$lat, $lon"
                url += "&key=${BuildConfig.MAPS_API_KEY_GOOGLE}"
                Picasso.get().load(url).into(mapView)
            }
        }
        // date
        rootView.findViewById<TextView>(R.id.item_detail_date_creation_txt).text = Utils.getFormatDate(it.realEstate.creationDate)
        if (it.realEstate.saleDate != null) {
            rootView.findViewById<TextView>(R.id.item_detail_date_sold_txt).text = Utils.getFormatDate(it.realEstate.saleDate)
            VISIBLE.also { rootView.findViewById<ImageView>(R.id.item_detail_sale_iv).visibility = it }
        } else {
            "".also { rootView.findViewById<TextView>(R.id.item_detail_date_sold_txt).text = it }
            "".also { rootView.findViewById<TextView>(R.id.item_detail_date_sold_title_txt).text = it }
            rootView.findViewById<ImageView>(R.id.item_detail_sale_iv).visibility = INVISIBLE
        }
        // realtor
        setUpRealtor(it.realEstate.idRealtor, rootView)
        // photo
        recyclerView = rootView.findViewById(R.id.item_detail_photos_rv)
        setUpRecyclerView(it.photos)
    }

    // ------------------
    // REALTOR
    // ------------------

    private fun setUpRealtor(id: Long, rootView: View){
        viewModel.getRealtor(id).observe(viewLifecycleOwner, {
            initNameRealtor(rootView, it.name)
        })
    }
    private fun initNameRealtor(rootView: View, name: String) {
        rootView.findViewById<TextView>(R.id.item_detail_realtor_creation_txt).text = name
    }

    private fun getCurrentRealtor(itemView: RealEstate, textView: TextView){
        viewModel.getRealtorCurrent().observe(requireActivity()) {
            val currentRealtor = it
            changeDevice(currentRealtor.prefEuro, itemView, textView  )
        }
    }

    // ------------------
    // DEVICE
    // ------------------

    @SuppressLint("SetTextI18n")
    private fun changeDevice(prefEuro: Boolean, itemView: RealEstate, textView: TextView) {
        if (prefEuro) {
            textView.text = Utils.convertDollarToEuro(itemView.price).toString() + "€"
        } else {
            textView.text = "$ "+ itemView.price
        }
    }
}