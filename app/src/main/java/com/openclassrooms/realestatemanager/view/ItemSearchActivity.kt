package com.openclassrooms.realestatemanager.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.view.itemList.ItemListActivity
import com.openclassrooms.realestatemanager.viewModel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class ItemSearchActivity: AppCompatActivity() {

    // --- FOR DATA ---

    private val viewModel : SearchViewModel by viewModel()

    private lateinit var switchSold : SwitchMaterial

    private lateinit var buttonDate : ImageView
    private lateinit var textDate : TextView
    private lateinit var newDate : Calendar

    private lateinit var checkBoxHouse : CheckBox
    private lateinit var checkBoxFlat : CheckBox
    private lateinit var checkBoxPenthouse : CheckBox
    private lateinit var checkBoxDuplex : CheckBox
    private var typeString: String = ""

    private lateinit var sliderPrice : RangeSlider
    private lateinit var sliderSurface : RangeSlider

    private lateinit var editTextRoom : TextInputEditText
    private lateinit var editTextBedroom : TextInputEditText
    private lateinit var editTextBathroom : TextInputEditText
    private lateinit var editTextCity : TextInputEditText

    private lateinit var checkBoxSchool : CheckBox
    private lateinit var checkBoxCommerce : CheckBox
    private lateinit var checkBoxPark : CheckBox

    private lateinit var checkBoxPhoto1 : CheckBox
    private lateinit var checkBoxPhoto2 : CheckBox
    private lateinit var checkBoxPhoto3 : CheckBox
    private var numberPhotoMin : Int = 0


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
        buttonDate = findViewById(R.id.search_RE_button_date_iv)
        textDate = findViewById(R.id.search_RE_text_date_txt)
        setUpDate()
        checkBoxHouse = findViewById(R.id.search_RE_type_house_cb)
        checkBoxFlat = findViewById(R.id.search_RE_type_flat_cb)
        checkBoxPenthouse = findViewById(R.id.search_RE_type_penthouse_cb)
        checkBoxDuplex = findViewById(R.id.search_RE_type_duplex_cb)
        sliderSurface = findViewById(R.id.search_RE_surface_Slider)
        sliderPrice = findViewById(R.id.search_RE_price_Slider)
        setUpDevice(getRealtorCurrent())
        editTextRoom = findViewById(R.id.search_RE_room_edit_text)
        editTextBedroom = findViewById(R.id.search_RE_bedrooms_edit_text)
        editTextBathroom = findViewById(R.id.search_RE_bathrooms_edit_text)
        editTextCity = findViewById(R.id.search_RE_city_edit_text)
        checkBoxSchool = findViewById(R.id.search_RE_school_cb)
        checkBoxCommerce = findViewById(R.id.search_RE_commerce_cb)
        checkBoxPark = findViewById(R.id.search_RE_park_cb)
        checkBoxPhoto1 = findViewById(R.id.search_RE_photo1_cb)
        checkBoxPhoto2 = findViewById(R.id.search_RE_photo2_cb)
        checkBoxPhoto3 = findViewById(R.id.search_RE_photo3_cb)
        setUpType()
        setUpPhoto()
        buttonClear = findViewById(R.id.search_RE_button_reset_iv)
        setUpClear()
        buttonFilter = findViewById(R.id.search_RE_button_validate_iv)
        setUpFilter()
    }
    // --- Device ---

    private fun setUpDevice(inEuro: Boolean) {
        val device : String = if(inEuro) {
            "EUR"
        }else{
            "USD"
        }
        sliderPrice.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance(device)
            format.format(value.toDouble())
        }
        sliderSurface.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("MET")
            format.format(value.toDouble())
        }
    }

    // --- TYPE ---

    private fun setUpType() {
        checkBoxFlat.setOnClickListener {
            checkBoxHouse.isChecked = false
            checkBoxPenthouse.isChecked = false
            checkBoxDuplex.isChecked = false
        }
        checkBoxHouse.setOnClickListener {
            checkBoxFlat.isChecked = false
            checkBoxPenthouse.isChecked = false
            checkBoxDuplex.isChecked = false
        }
        checkBoxPenthouse.setOnClickListener {
            checkBoxHouse.isChecked = false
            checkBoxFlat.isChecked = false
            checkBoxDuplex.isChecked = false
        }
        checkBoxDuplex.setOnClickListener {
            checkBoxHouse.isChecked = false
            checkBoxPenthouse.isChecked = false
            checkBoxFlat.isChecked = false
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

    // --- DATE ---

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setUpDate(){
        buttonDate.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val mYear = calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,{ _, year, monthOfYear, dayOfMonth ->
                textDate.text = "$dayOfMonth/$monthOfYear/$year"
                newDate.set(year,monthOfYear,dayOfMonth)
            }, mYear,mMonth,mDay)
            datePickerDialog.show()
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

    private fun setUpFilter(){
        buttonFilter.setOnClickListener{
            searchQuery()
        }
    }

    // ------------------
    // SEARCH
    // ------------------

    // --- Values --

    private fun typeString(): String {
        if(checkBoxFlat.isChecked){typeString = "Flat" }
        if(checkBoxHouse.isChecked){typeString = "House" }
        if(checkBoxPenthouse.isChecked){ typeString = "Penthouse"}
        if(checkBoxDuplex.isChecked){ typeString = "Duplex"}
        return typeString

    }

    private fun numberPhotoMin(): Int{
        if(checkBoxPhoto1.isChecked){numberPhotoMin = 1 }
        if(checkBoxPhoto2.isChecked){numberPhotoMin = 2 }
        if(checkBoxPhoto3.isChecked){ numberPhotoMin = 3}
        return numberPhotoMin
    }

    private fun searchQuery(){
        val sold = switchSold.isChecked
        val type = typeString()
        val priceMin = sliderPrice.values[0].toInt()
        val priceMax = sliderPrice.values[1].toInt()
        val surfaceMin = sliderSurface.values[0].toInt()
        val surfaceMax = sliderSurface.values[1].toInt()
        val roomMin = editTextRoom.text.toString().toInt()
        val bedroomMin = editTextBedroom.text.toString().toInt()
        val bathroomMin = editTextBathroom.text.toString().toInt()
        val city = editTextCity.text.toString()
        val school = checkBoxSchool.isChecked
        val commerce = checkBoxCommerce.isChecked
        val park = checkBoxPark.isChecked
        val minPhoto = numberPhotoMin()
        val dateLong = newDate.timeInMillis

        var query = "SELECT *,(SELECT COUNT(*) FROM Photo.idRealEstate = RealEstate.id) AS count_photos FROM Real_Estate"
        val args = arrayListOf<Any>()
        var conditions = false

        if (type != ""){
            query += " WHERE estateType = :$type"
            args.add(type)
            conditions = true
        }

        if ((sold)){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "isSold = :$sold"
            args.add(sold)
        }

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "price >= :${priceMin}"
        args.add(priceMin)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "price <= :${priceMax}"
        args.add(priceMax)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "area >= :$surfaceMin"
        args.add(surfaceMin)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "area <= :$surfaceMax"
        args.add(surfaceMax)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "numberRoom >= :$roomMin"
        args.add(roomMin)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "numberBedroom <= :$bedroomMin"
        args.add(bedroomMin)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "numberBathroom <= :$bathroomMin"
        args.add(bathroomMin)

        if (city != ""){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "city = ?"
            args.add(city)
        }

        if (school){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "closeToPark = :$school"
            args.add(school)
        }

        if (commerce){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "closeToCommerce = :$commerce"
            args.add(commerce)
        }

        if (park){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "closeToSchool = :$park"
            args.add(park)
        }

        query += if (conditions) " AND " else " WHERE "
        query += "creationDate >= ?"
        args.add(dateLong)

        query += " AND count_photos >= ?"
        args.add(minPhoto)

        viewModel.getEstatesBySearch(query,args).observe(this, {
            if(it!!.isNotEmpty()){
                this.launchList(query,args)
            }else{
                Toast.makeText(this,resources.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
            }
        })

    }

    // ------------------
    // LAUNCH
    // ------------------

    private fun launchList(query: String, args: ArrayList<Any>) {
        intent = Intent(this, ItemListActivity::class.java)
        intent.putExtra("QUERY", query)
        intent.putExtra("ARGS", args)
        startActivity(intent)
    }



}