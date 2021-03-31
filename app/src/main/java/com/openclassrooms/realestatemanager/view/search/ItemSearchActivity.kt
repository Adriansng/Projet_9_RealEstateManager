package com.openclassrooms.realestatemanager.view.search

import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.slider.RangeSlider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.viewModel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class ItemSearchActivity: AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_search)
        title = this.getString(R.string.filter_tilte)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpUi()
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

    private fun setUpUi(){
        switchSold = findViewById(R.id.search_RE_sold_switch)
        checkBoxHouse = findViewById(R.id.search_RE_type_house_cb)
        checkBoxFlat = findViewById(R.id.search_RE_type_flat_cb)
        checkBoxPenthouse = findViewById(R.id.search_RE_type_penthouse_cb)
        checkBoxDuplex = findViewById(R.id.search_RE_type_duplex_cb)
        sliderSurface = findViewById(R.id.search_RE_surface_Slider)
        setUpDevice(getRealtorCurrent())
        priceMinLayout = findViewById(R.id.search_RE_price_min_txt)
        priceMinLayout = findViewById(R.id.search_RE_price_max_text)
        editTextPriceMin = findViewById(R.id.search_RE_price_min_edit_text)
        editTextPriceMax = findViewById(R.id.search_RE_price_max_edit_text)
        editTextRoom = findViewById(R.id.search_RE_room_edit_text)
        editTextBedroom = findViewById(R.id.search_RE_bedrooms_edit_text)
        editTextBathroom = findViewById(R.id.search_RE_bathrooms_edit_text)
        checkBoxSchool = findViewById(R.id.search_RE_school_cb)
        checkBoxCommerce = findViewById(R.id.search_RE_commerce_cb)
        checkBoxPark = findViewById(R.id.search_RE_park_cb)
        checkBoxPhoto1 = findViewById(R.id.search_RE_photo1_cb)
        checkBoxPhoto2 = findViewById(R.id.search_RE_photo2_cb)
        checkBoxPhoto3 = findViewById(R.id.search_RE_photo3_cb)
        setUpPhoto()
        buttonClear = findViewById(R.id.search_RE_button_reset_iv)
        setUpClear()
        buttonFilter = findViewById(R.id.search_RE_button_validate_iv)
    }
    // --- Device ---

    private fun setUpDevice(inEuro: Boolean) {
        if(inEuro) {
            priceMinLayout.startIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_euro_24)
            priceMaxLayout.startIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_euro_24)
        }
        sliderSurface.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("m")
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