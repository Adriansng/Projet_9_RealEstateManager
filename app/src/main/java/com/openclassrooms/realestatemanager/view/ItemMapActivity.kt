package com.openclassrooms.realestatemanager.view

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.itemDetail.ItemDetailActivity
import com.openclassrooms.realestatemanager.view.itemDetail.ItemDetailFragment
import com.openclassrooms.realestatemanager.viewModel.MapViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class ItemMapActivity : AppCompatActivity(), OnMapReadyCallback {

    // --- FOR DATA ---
    private val viewModel : MapViewModel by viewModel()


    // ---- FOR DATA ---
    private var mapGoogle: GoogleMap? = null

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        title = applicationContext.getString(R.string.map_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configMap()
        setUpRealEstates()

    }

    // ------------------
    // REAL ESTATE
    // ------------------

    private fun setUpRealEstates(){
        viewModel.getRealEstates().observe(this, {
            getMarkers(it)
        })
    }

    // ------------------
    // GOOGLE MAP
    // ------------------

    // --- MAP ---
    private fun configMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        mapGoogle = googleMap
        val mLocation: Location = Utils.getLastKnowLocation(this, this)
        mapGoogle!!.isMyLocationEnabled = true
        zoomLocation(mLocation)
    }

    // --- ZOOM LOCATION ---
    private fun zoomLocation(location: Location) {
        mapGoogle!!.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude), 15f))
    }

    // ------------------
    // MARKER REAL ESTATE
    // ------------------

    // --- MARKERS ---

    private fun getMarkers(realEstate: List<RealEstate>) {
        if (mapGoogle != null) mapGoogle!!.clear()
        val size = realEstate.size
        for (i in 0 until size) {
            val marker: RealEstate = realEstate[i]
                mapGoogle!!.addMarker(MarkerOptions()
                        .position(marker.location!!)
                        .title(marker.price.toString()))

            clickOnMarker(mapGoogle!!, realEstate)
        }
    }

    // --- CLICK MARKER ---
    private fun clickOnMarker(mapGoogle: GoogleMap, realEstate: List<RealEstate>) {
        mapGoogle.setOnMarkerClickListener { marker: Marker ->
            val intent = Intent(this, ItemDetailActivity::class.java)
            val size = realEstate.size
            for (e in 0 until size) {
                if (realEstate[e].price.toString() == marker.title) {
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, realEstate[e].id.toString())
                    Objects.requireNonNull(this).startActivity(intent)
                }
            }
            Toast.makeText(this, marker.title, Toast.LENGTH_SHORT).show()
            false
        }
    }


}
