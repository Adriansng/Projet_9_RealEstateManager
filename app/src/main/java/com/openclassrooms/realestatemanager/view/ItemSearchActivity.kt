package com.openclassrooms.realestatemanager.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
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
import java.text.SimpleDateFormat
import java.util.*

class ItemSearchActivity: AppCompatActivity() {

    // --- FOR DATA ---

    private val viewModel : SearchViewModel by viewModel()

    private lateinit var switchSold : SwitchMaterial

    private lateinit var buttonDate : ImageView
    private lateinit var textDate : TextView
    private var newDate : Date? = null

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

    private lateinit var sharedPreferences: SharedPreferences

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_search)
        title = this.getString(R.string.filter_tilte)
        getRealtorCurrent()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = this.getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        setUpUi()
    }

    // ------------------
    // REALTOR CURRENT
    // ------------------

    private fun getRealtorCurrent(){
          viewModel.getRealtorCurrent().observe(this, {
          setUpDevice(it.prefEuro)
        })
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
        getSharedPreferences()
    }

    // --- DEVICE ---

    private fun setUpDevice(inEuro: Boolean) {
        val device : String = if(inEuro) {
            "EUR"
        }else{
            "USD"
        }
        sliderPrice.setLabelFormatter { value: Float ->
            val formatPrice = NumberFormat.getCurrencyInstance()
            formatPrice.maximumFractionDigits = 0
            formatPrice.currency = Currency.getInstance(device)
            formatPrice.format(value.toDouble())
        }
        sliderSurface.setLabelFormatter { value: Float ->
            return@setLabelFormatter "${value}m²"
        }
    }

    // --- TYPE ---

    private fun setUpType() {
        val shareType = sharedPreferences.getString("type_key","")
        if(shareType != ""){
            if(shareType == "House"){
                checkBoxHouse.isChecked = true
            }
            if(shareType == "Flat"){
                checkBoxFlat.isChecked = true
            }
            if(shareType == "Penthouse"){
                checkBoxPenthouse.isChecked = true
            }
            if(shareType == "Duplex"){
                checkBoxDuplex.isChecked = true
            }
        }
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
        val sharePhoto = sharedPreferences.getInt("photo_key", 1)
        if (sharePhoto == 1) {
            checkBoxPhoto1.isChecked = true
        }
        if(sharePhoto == 2){
            checkBoxPhoto2.isChecked = true
        }
        if(sharePhoto == 3){
            checkBoxPhoto3.isChecked = true
        }
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
        val shareDate= sharedPreferences.getLong("date_key", 0L)
        val format = SimpleDateFormat("dd.MM.yyyy")
        if(shareDate != 0L){
            val date = Date(shareDate)
            textDate.text = format.format(date)
            newDate = Date(shareDate)
        }
        buttonDate.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val mYear = calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,{ _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.YEAR, year)
                textDate.text = format.format(calendar.time)
                newDate = calendar.time
            }, mYear,mMonth,mDay)
            datePickerDialog.show()
        }
    }

    // --- SHARED PREFERENCES ---

    private fun getSharedPreferences(){
        switchSold.isChecked = sharedPreferences.getBoolean("sold_key", false)
        checkBoxSchool.isChecked = sharedPreferences.getBoolean("school_key", false)
        checkBoxCommerce.isChecked = sharedPreferences.getBoolean("commerce_key", false)
        checkBoxPark.isChecked = sharedPreferences.getBoolean("park_key", false)
        val sharedRoom = sharedPreferences.getInt("room_key", 0)
        val sharedBedroom = sharedPreferences.getInt("bedroom_key", 0)
        val sharedBathroom = sharedPreferences.getInt("bathroom_key", 0)
        if(sharedRoom != 0 ){
            editTextRoom.setText(sharedRoom.toString(),TextView.BufferType.EDITABLE)
        }
        if(sharedBedroom != 0 ){
            editTextBedroom.setText(sharedBedroom.toString(),TextView.BufferType.EDITABLE)
        }
        if(sharedBathroom != 0 ){
            editTextBathroom.setText(sharedBathroom.toString(),TextView.BufferType.EDITABLE)
        }
        val sharedPriceMin= sharedPreferences.getInt("price_min_key", 0)
        val sharedPriceMax = sharedPreferences.getInt("price_max_key", 900000000)
        val sharedSurfaceMin = sharedPreferences.getInt("surface_min_key", 0)
        val sharedSurfaceMax = sharedPreferences.getInt("surface_max_key", 1000)
        sliderPrice.valueFrom = sharedPriceMin.toFloat()
        sliderPrice.valueTo = sharedPriceMax.toFloat()
        sliderSurface.valueFrom = sharedSurfaceMin.toFloat()
        sliderSurface.valueTo = sharedSurfaceMax.toFloat()

        val sharedCity = sharedPreferences.getString("city_key", "")
        if(sharedCity != "" ){
            editTextCity.setText(sharedCity,TextView.BufferType.EDITABLE)
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
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        viewModel.initSearchList()
        launchList()
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

    // --- VALUES --

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
        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        val sold = switchSold.isChecked
        editor.putBoolean("sold_key", sold)
        val type = typeString()
        editor.putString("type_key", type)
        val priceMin = sliderPrice.values[0].toInt()
        editor.putInt("price_min_key", priceMin)
        val priceMax = sliderPrice.values[1].toInt()
        editor.putInt("price_max_key", priceMax)
        val surfaceMin = sliderSurface.values[0].toInt()
        editor.putInt("surface_min_key", surfaceMin)
        val surfaceMax = sliderSurface.values[1].toInt()
        editor.putInt("surface_max_key", surfaceMax)
        val roomMin = if((editTextRoom.text.toString() == "")){
            0
        }else{
            editTextRoom.text.toString().toInt()
        }
        editor.putInt("room_key", roomMin)
        val bedroomMin = if ((editTextBedroom.text.toString() == "")) {
            0
        } else {
            editTextBedroom.text.toString().toInt()
        }
        editor.putInt("bedroom_key", bedroomMin)
        val bathroomMin = if ((editTextBathroom.text.toString() == "")) {
            0
        } else {
            editTextBathroom.text.toString().toInt()
        }
        editor.putInt("bathroom_key", bathroomMin)
        val city = editTextCity.text.toString()
        editor.putString("city_key", city)
        val school = checkBoxSchool.isChecked
        editor.putBoolean("school_key", school)
        val commerce = checkBoxCommerce.isChecked
        editor.putBoolean("commerce_key", commerce)
        val park = checkBoxPark.isChecked
        editor.putBoolean("park_key", park)
        val minPhoto = numberPhotoMin()
        editor.putInt("photo_key", minPhoto)
        val dateLong : Long
        if(newDate != null){
            dateLong = newDate!!.time
            editor.putLong("date_key", newDate!!.time)
        }else{
            dateLong = 0L
        }
        editor.apply()
        editor.commit()

        var query = "SELECT * , (SELECT COUNT(*) FROM Photo WHERE Photo.realEstate_id = Real_Estate.realEstate_id) AS photos_count FROM Real_Estate"
        val args = arrayListOf<Any>()
        var conditions = false

        if (type != ""){
            query += " WHERE type = ?"
            args.add(type)
            conditions = true
        }

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "isSold = ?"
        args.add(sold)

        if (priceMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "price >= ?"
            args.add(priceMin)
        }
        if (priceMax != 900000000) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "price <= ?"
            args.add(priceMax)
        }

        if (surfaceMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "area >= ?"
            args.add(surfaceMin)
        }

        if (surfaceMax != 1000) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "area <= ?"
            args.add(surfaceMax)
        }

        if (roomMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "numberRoom >= ?"
            args.add(roomMin)
        }
        if (bedroomMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "numberBedroom >= ?"
            args.add(bedroomMin)
        }
        if (bathroomMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "numberBathroom >= ?"
            args.add(bathroomMin)
        }

        if (city != ""){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "city = ?"
            args.add(city)
        }

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "closeToSchool = ?"
        args.add(school)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "closeToCommerce = ?"
        args.add(commerce)

        query += if (conditions) " AND " else " WHERE "
        conditions = true
        query += "closeToPark = ?"
        args.add(park)

        if (dateLong != 0L) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "creationDate >= :${dateLong}"
            args.add(dateLong)
        }

        query += if (conditions) " AND " else " WHERE "
        query += "photos_count >= ?"
        args.add(minPhoto)

        viewModel.getEstatesBySearch(query,args).observe(this, {
            if(it!!.isNotEmpty()){
                viewModel.setSearchList(it)
                this.launchList()
            }else{
                Toast.makeText(this,resources.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
            }
        })

    }

    // ------------------
    // ACTIVITY
    // ------------------

    private fun launchList() {
        intent = Intent(this, ItemListActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}