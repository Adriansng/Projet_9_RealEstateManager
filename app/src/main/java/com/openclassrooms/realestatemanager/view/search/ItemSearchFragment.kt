package com.openclassrooms.realestatemanager.view.search

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.slider.RangeSlider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.viewModel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class ItemSearchFragment: Fragment() {

    // --- FOR DATA ---

    private val viewModel : SearchViewModel by viewModel()

    private lateinit var switchSold: SwitchMaterial

    private lateinit var checkBoxHouse : CheckBox
    private lateinit var checkBoxFlat : CheckBox
    private lateinit var checkBoxPenthouse : CheckBox
    private lateinit var checkBoxDuplex : CheckBox

    private lateinit var priceMinLayout: TextInputLayout
    private lateinit var priceMaxLayout: TextInputLayout
    private lateinit var editTextPriceMin : TextInputEditText
    private lateinit var editTextPriceMax : TextInputEditText

    private lateinit var sliderSurface : RangeSlider

    private lateinit var editTextRoom : TextInputEditText
    private lateinit var editTextBedroom : TextInputEditText
    private lateinit var editTextBathroom : TextInputEditText

    private lateinit var checkBoxSchool : CheckBox
    private lateinit var checkBoxCommerce : CheckBox
    private lateinit var checkBoxPark : CheckBox

    private lateinit var checkBoxPhoto1 : CheckBox
    private lateinit var checkBoxPhoto2 : CheckBox
    private lateinit var checkBoxPhoto3 : CheckBox


    private lateinit var buttonClear : ImageView
    private lateinit var buttonFilter : ImageView


    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.activity_item_search, container, false)

        setUpUi(view)
        return view
    }

    // ------------------
    // REALTOR CURRENT
    // ------------------

    private fun getRealtorCurrent(): Boolean{
        return  viewModel.getRealtorCurrent().value!!.prefEuro
    }

    // ------------------
    // UI
    // ------------------

    private fun setUpUi(view: View){
        switchSold = view.findViewById(R.id.search_RE_sold_switch)
        checkBoxHouse = view.findViewById(R.id.search_RE_type_house_cb)
        checkBoxFlat = view.findViewById(R.id.search_RE_type_flat_cb)
        checkBoxPenthouse = view.findViewById(R.id.search_RE_type_penthouse_cb)
        checkBoxDuplex = view.findViewById(R.id.search_RE_type_duplex_cb)
        sliderSurface = view.findViewById(R.id.search_RE_surface_Slider)
        setUpDevice(getRealtorCurrent())
        priceMinLayout = view.findViewById(R.id.add_RE_price_text)
        priceMinLayout = view.findViewById(R.id.search_RE_price_max_text)
        editTextPriceMin = view.findViewById(R.id.search_RE_price_min_edit_text)
        editTextPriceMax = view.findViewById(R.id.search_RE_price_max_edit_text)
        editTextRoom = view.findViewById(R.id.search_RE_room_edit_text)
        editTextBedroom = view.findViewById(R.id.search_RE_bedrooms_edit_text)
        editTextBathroom = view.findViewById(R.id.search_RE_bathrooms_edit_text)
        checkBoxSchool = view.findViewById(R.id.search_RE_school_cb)
        checkBoxCommerce = view.findViewById(R.id.search_RE_commerce_cb)
        checkBoxPark = view.findViewById(R.id.search_RE_park_cb)
        checkBoxPhoto1 = view.findViewById(R.id.search_RE_photo1_cb)
        checkBoxPhoto2 = view.findViewById(R.id.search_RE_photo2_cb)
        checkBoxPhoto3 = view.findViewById(R.id.search_RE_photo3_cb)
        setUpPhoto()
        buttonClear = view.findViewById(R.id.search_RE_button_reset_iv)
        setUpClear()
        buttonFilter = view.findViewById(R.id.search_RE_button_validate_iv)
    }
    // --- Device ---

    private fun setUpDevice(inEuro: Boolean) {
        val device : String
        if(inEuro){
            priceMinLayout.startIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_euro_24)
            priceMaxLayout.startIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_euro_24)
            device = "€"
        }else{
            device =  "$"
        }
        sliderSurface.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance(device)
            format.format(value.toDouble())
        }
    }

    // --- PHOTO ---

    private fun setUpPhoto() {
        checkBoxPhoto1.setOnClickListener {
            checkBoxPhoto2.isChecked = false
            checkBoxPhoto3.isChecked = false
        }
        checkBoxPhoto2.setOnClickListener {
            checkBoxPhoto1.isChecked = false
            checkBoxPhoto3.isChecked = false
        }
        checkBoxPhoto3.setOnClickListener {
            checkBoxPhoto2.isChecked = false
            checkBoxPhoto1.isChecked = false
        }
    }

    // --- RESET ---

    private fun resetUi(){
        switchSold.isChecked = false
        checkBoxHouse.isChecked = false
        checkBoxFlat.isChecked = false
        checkBoxPenthouse.isChecked = false
        checkBoxDuplex.isChecked = false
        checkBoxSchool.isChecked = false
        checkBoxCommerce.isChecked = false
        checkBoxPark.isChecked = false
        checkBoxPhoto1.isChecked = false
        checkBoxPhoto2.isChecked = false
        checkBoxPhoto3.isChecked = false
        sliderSurface.valueFrom = 20F
        sliderSurface.valueTo = 1000F
        editTextPriceMin.text?.clear()
        editTextPriceMax.text?.clear()
        editTextRoom.text?.clear()
        editTextBathroom.text?.clear()
        editTextBedroom.text?.clear()
    }
    // --- BUTTON CLEAR ---

    private fun setUpClear(){
        buttonClear.setOnClickListener{
            resetUi()
        }
    }

    // --- BUTTON FILTER ---

}