package com.openclassrooms.realestatemanager.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.itemDetail.ItemDetailFragment
import com.openclassrooms.realestatemanager.view.itemList.ItemListActivity
import com.openclassrooms.realestatemanager.viewModel.ItemCreationViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ItemCreationRealEstateActivity : AppCompatActivity() {


    private val viewModel : ItemCreationViewModel by viewModel()

    private lateinit var currentRealtor: Realtor

    private lateinit var soldSwitchMaterial: SwitchMaterial

    private lateinit var typeAutoCompleteTextView: AutoCompleteTextView

    private lateinit var priceEdit: TextInputEditText
    private lateinit var surfaceEdit: TextInputEditText
    private lateinit var roomEdit: TextInputEditText
    private lateinit var bedroomEdit: TextInputEditText
    private lateinit var bathroomEdit: TextInputEditText
    private lateinit var addressEdit: TextInputEditText
    private lateinit var cityEdit: TextInputEditText
    private lateinit var zipEdit: TextInputEditText
    private lateinit var descriptionEdit: TextInputEditText


     private lateinit var closeToSchool: CheckBox
     private lateinit var closeToCommerce: CheckBox
     private lateinit var closeToPark: CheckBox


    private lateinit var button: ImageView

    private lateinit var realEstate : RealEstate

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_item_creation)
        title = this.getString(R.string.add_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCurrentRealtor()
        setUpUi()
        val realEstateId = intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID)
        if (realEstateId != null ){
            realEstate = getRealEstate(realEstateId.toString().toLong())
            editRealEstate(realEstate)
        }
    }

    // ------------------
    // UI
    // ------------------

    private fun setUpUi(){
        soldSwitchMaterial = findViewById(R.id.add_RE_sold_switch)
        setUpSwitch()
        typeAutoCompleteTextView = findViewById(R.id.add_RE_type_spinner)
        setUpDropDownMenu()
        priceEdit = findViewById(R.id.add_RE_price_edit_text)
        setUpDevice()
        surfaceEdit = findViewById(R.id.add_RE_surface_edit_text)
        roomEdit = findViewById(R.id.add_RE_room_edit_text)
        bedroomEdit = findViewById(R.id.add_RE_bedrooms_edit_text)
        bathroomEdit = findViewById(R.id.add_RE_bathrooms_edit_text)
        addressEdit = findViewById(R.id.add_RE_address_edit_text)
        cityEdit = findViewById(R.id.add_RE_city_edit_text)
        zipEdit = findViewById(R.id.add_RE_zip_edit_text)
        descriptionEdit = findViewById(R.id.add_RE_description_edit_text)
        setUpEditText()

        closeToSchool = findViewById(R.id.add_RE_school_cb)
        closeToCommerce = findViewById(R.id.add_RE_commerce_cb)
        closeToPark = findViewById(R.id.add_RE_park_cb)
        button = findViewById(R.id.add_RE_button_add_iv)
        button.setOnClickListener { checkCalculator() }

    }

    // --- SWITCH ---

    private fun setUpSwitch(){
        soldSwitchMaterial.isChecked = realEstate.saleCreation != null
    }

    // --- EDIT TEXT ---

    private fun setUpEditText(){
        editText(priceEdit, applicationContext.getString(R.string.add_price))
        editText(surfaceEdit, applicationContext.getString(R.string.add_surface))
        editText(roomEdit, applicationContext.getString(R.string.add_rooms))
        editText(bedroomEdit, applicationContext.getString(R.string.add_bedrooms))
        editText(bathroomEdit, applicationContext.getString(R.string.add_bathrooms))
        editText(addressEdit, applicationContext.getString(R.string.add_address))
        editText(cityEdit, applicationContext.getString(R.string.add_city))
        editText(zipEdit, applicationContext.getString(R.string.add_zip_code))
        editText(descriptionEdit, applicationContext.getString(R.string.add_description))
    }

    private fun editText(editText: TextInputEditText, text: String) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()){
                    editText.error = "$text is required."
                }else{
                    editText.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    // --- DROPDOWN MENU ---

    private fun setUpDropDownMenu(){
        val items = listOf(
                "House",
                "Flat",
                "Duplex",
                "Penthouse",)
        val adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, items)
        typeAutoCompleteTextView.setAdapter(adapter)
    }

    // --- DEVICE ---

    private fun setUpDevice(){
        if(currentRealtor.prefEuro){
            val priceLayout = findViewById<TextInputLayout>(R.id.add_RE_price_text)
            priceLayout.startIconDrawable = ContextCompat.getDrawable(this,R.drawable.ic_baseline_euro_24)
        }
    }


    // ------------------
    // SET UP EDIT
    // ------------------

    private fun editRealEstate(realEstate: RealEstate){
        typeAutoCompleteTextView.setText(realEstate.type)
        if(currentRealtor.prefEuro){
            priceEdit.setText(Utils.convertDollarToEuro(realEstate.price))
        }else{
            priceEdit.setText(realEstate.price)
        }
        surfaceEdit.setText(realEstate.area)
        roomEdit.setText(realEstate.price)
        bedroomEdit.setText(realEstate.numberBedroom)
        bathroomEdit.setText(realEstate.numberBathroom)
        addressEdit.setText(realEstate.address)
        cityEdit.setText(realEstate.city)
        zipEdit.setText(realEstate.zipCode)
        descriptionEdit.setText(realEstate.descriptionRealEstate)
        closeToSchool.isChecked = realEstate.closeToSchool
        closeToCommerce.isChecked = realEstate.closeToCommerce
        closeToPark.isChecked = realEstate.closeToPark
        if(realEstate.saleCreation!= null) soldSwitchMaterial.isChecked = true
    }


    // ------------------
    // CHECK FORM
    // ------------------

    private fun checkCalculator(){
        if(typeAutoCompleteTextView.text.toString() != ("") &&
                priceEdit.text.toString() != ("") &&
                cityEdit.text.toString() != ("") &&
                surfaceEdit.text.toString() != ("")&&
                roomEdit.text.toString() != ("") &&
                bathroomEdit.text.toString() != ("") &&
                bedroomEdit.text.toString() != ("") &&
                addressEdit.text.toString() != ("") &&
                zipEdit.text.toString() != ("") &&
                descriptionEdit.text.toString() != ("")){
            addRealEstate()
        }else{
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    // ------------------
    // ADD REAL ESTATE
    // ------------------

    private fun addRealEstate(){
        if(soldSwitchMaterial.isChecked){
            realEstate.saleCreation = Date().time
        }
        realEstate.type = typeAutoCompleteTextView.text.toString()
        if(currentRealtor.prefEuro){
        realEstate.price = Utils.convertEuroToDollar(priceEdit.text.toString().toInt())
        }else{
            realEstate.price = priceEdit.text.toString().toInt()
        }
        realEstate.area = surfaceEdit.text.toString().toInt()
        realEstate.numberRoom = roomEdit.text.toString().toInt()
        realEstate.numberBedroom = bedroomEdit.text.toString().toInt()
        realEstate.numberBathroom = bathroomEdit.text.toString().toInt()
        realEstate.address = addressEdit.text.toString()
        realEstate.city = cityEdit.text.toString()
        realEstate.zipCode = zipEdit.text.toString()
        realEstate.descriptionRealEstate = descriptionEdit.text.toString()
        realEstate.closeToSchool = closeToSchool.isChecked
        realEstate.closeToCommerce = closeToCommerce.isChecked
        realEstate.closeToPark = closeToPark.isChecked
        realEstate.idRealtor = this.currentRealtor.id
        viewModel.addRealEstate(realEstate)
        finishActivity()
    }


    // ------------------
    // REAL ESTATE
    // ------------------

    private fun getRealEstate(id : Long): RealEstate = viewModel.getRealEstate(id)

    // ------------------
    // REALTOR
    // ------------------
    private fun getCurrentRealtor(){
        viewModel.getRealtorCurrent().observe(this, {
            currentRealtor = it
        })
    }
    // ------------------
    // FINISH ACTIVITY
    // ------------------
    private fun finishActivity(){
        navigateUpTo(Intent(this, ItemListActivity::class.java))
    }
}
