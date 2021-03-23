package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.utils.Enum
import com.openclassrooms.realestatemanager.viewModel.ItemCreationViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

 class ItemCreationRealEstate : AppCompatActivity() {


    private val viewModel : ItemCreationViewModel by viewModel()


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

    private lateinit var button: ImageView

    private var realEstate = RealEstate.default()
    private var realtorId : Long = 0

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_item_creation)
        title = this.getString(R.string.add_title)
        realtorId = intent.getLongExtra("Realtor", 0)
        //realEstate = intent.getSerializableExtra("RealEstate") as RealEstate
        setUpUi()
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
        surfaceEdit = findViewById(R.id.add_RE_surface_edit_text)
        roomEdit = findViewById(R.id.add_RE_room_edit_text)
        bedroomEdit = findViewById(R.id.add_RE_bedrooms_edit_text)
        bathroomEdit = findViewById(R.id.add_RE_bathrooms_edit_text)
        addressEdit = findViewById(R.id.add_RE_address_edit_text)
        cityEdit = findViewById(R.id.add_RE_city_edit_text)
        zipEdit = findViewById(R.id.add_RE_zip_edit_text)
        descriptionEdit = findViewById(R.id.add_RE_description_edit_text)
        button = findViewById(R.id.add_RE_button_add_iv)

        button.setOnClickListener { checkCalculator() }

        setUpEditText()
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
        val adapter = ArrayAdapter(applicationContext, R.layout.item_spinner, items)
        typeAutoCompleteTextView.setAdapter(adapter)
    }
    // ------------------
    // CHECK FORM
    // ------------------

    private fun checkCalculator(){
        if(typeAutoCompleteTextView.text.toString() != ("") &&
                priceEdit.text.toString() != ("") &&
                addressEdit.text.toString() != ("") &&
                surfaceEdit.text.toString() != ("")&&
                roomEdit.text.toString() != ("") &&
                bathroomEdit.text.toString() != ("") &&
                bedroomEdit.text.toString() != ("") &&
                addressEdit.text.toString() != ("") &&
                zipEdit.text.toString() != ("") &&
                descriptionEdit.text.toString() != ("")){
            addRealEstate()
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
        realEstate.price = priceEdit.text.toString().toInt()
        realEstate.area = surfaceEdit.text.toString().toInt()
        realEstate.numberRoom = roomEdit.text.toString().toInt()
        realEstate.numberBedroom = bedroomEdit.text.toString().toInt()
        realEstate.numberBathroom = bathroomEdit.text.toString().toInt()
        realEstate.address = addressEdit.text.toString()
        realEstate.city = cityEdit.text.toString()
        realEstate.zipCode = zipEdit.text.toString()
        realEstate.descriptionRealEstate = descriptionEdit.text.toString()
        realEstate.idRealtor = this.realtorId
        viewModel.addRealEstate(realEstate)

    }
}
