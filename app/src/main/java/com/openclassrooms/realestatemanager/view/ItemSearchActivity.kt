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
    private var newDate : Calendar? = null

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = this.getSharedPreferences("sharedPreferences", MODE_PRIVATE)
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
        getSharedPreferences()
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
            format.format(value.toDouble())
        }
    }

    // --- TYPE ---

    private fun setUpType() {
        val shareType = sharedPreferences.getString("type_key","")
        if(shareType != ""){
            if(shareType == "House"){
                checkBoxHouse.isChecked
            }
            if(shareType == "Flat"){
                checkBoxFlat.isChecked
            }
            if(shareType == "Penthouse"){
                checkBoxPenthouse.isChecked
            }
            if(shareType == "Duplex"){
                checkBoxDuplex.isChecked
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
        if(sharePhoto != 1){
            if(sharePhoto == 2){
                checkBoxPhoto2.isChecked
            }
            if(sharePhoto == 3){
                checkBoxPhoto3.isChecked
            }
        }else{
            checkBoxPhoto1.isChecked
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
        if(shareDate != 0L){
            val date = Date(shareDate)
            val format = SimpleDateFormat("d/m/yyyy")
            textDate.text = format.format(date)
            newDate?.timeInMillis = shareDate
        }
        buttonDate.setOnClickListener{
            newDate = Calendar.getInstance()
            val calendar: Calendar = Calendar.getInstance()
            val mYear = calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,{ _, year, monthOfYear, dayOfMonth ->
                textDate.text = "$dayOfMonth/$monthOfYear/$year"
                newDate?.set(year,monthOfYear,dayOfMonth)
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
            editTextRoom.setText(sharedRoom,TextView.BufferType.EDITABLE)
        }
        if(sharedBedroom != 0 ){
            editTextBedroom.setText(sharedBedroom,TextView.BufferType.EDITABLE)
        }
        if(sharedBathroom != 0 ){
            editTextBathroom.setText(sharedBathroom,TextView.BufferType.EDITABLE)
        }
        val sharedPriceMin= sharedPreferences.getInt("price_min_key", 0)
        val sharedPriceMax = sharedPreferences.getInt("price_max_key", 900000000)
        val sharedSurfaceMin = sharedPreferences.getInt("surface_min_key", 0)
        val sharedSurfaceMax = sharedPreferences.getInt("surface_max_key", 1000)
        sliderPrice.values[0] = sharedPriceMin.toFloat()
        sliderPrice.values[1] = sharedPriceMax.toFloat()
        sliderSurface.values[0] = sharedSurfaceMin.toFloat()
        sliderSurface.values[1] = sharedSurfaceMax.toFloat()

        val sharedCity = sharedPreferences.getString("city_key", "")
        if(sharedCity != "" ){
            editTextCity.setText(sharedCity,TextView.BufferType.EDITABLE)
        }
    }

    // --- RESET ---

    @SuppressLint("CommitPrefEdits")
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
            editTextRoom . text . toString ().toInt()
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
        val dateLong = if(newDate != null){
            newDate?.timeInMillis
        }else{
            null
        }
        editor.putLong("date_key", dateLong!!)
        editor.apply()
        editor.commit()

        var query = "SELECT * , (SELECT COUNT(*) FROM Photo WHERE Photo.realEstate_id = Real_Estate.realEstate_id) AS count_photos FROM Real_Estate"
        val args = arrayListOf<Any>()
        var conditions = false

        if (type != ""){
            query += " WHERE type = :${type}"
            args.add(type)
            conditions = true
        }

        if ((sold)){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "isSold = :${sold}"
            args.add(sold)
        }

        if (priceMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "price >= :${priceMin}"
            args.add(priceMin)
        }
        if (priceMax != 900000000) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "price <= :${priceMax}"
            args.add(priceMax)
        }

        if (surfaceMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "area >= :${surfaceMin}"
            args.add(surfaceMin)
        }

        if (surfaceMax != 1000) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "area <= :${surfaceMax}"
            args.add(surfaceMax)
        }

        if (roomMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "numberRoom >= :${roomMin}"
            args.add(roomMin)
        }
        if (bedroomMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "numberBedroom >= :${bedroomMin}"
            args.add(bedroomMin)
        }
        if (bathroomMin != 0) {
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "numberBathroom >= :${bathroomMin}"
            args.add(bathroomMin)
        }

        if (city != ""){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "city = :${city}"
            args.add(city)
        }

        if (school){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "closeToPark = :${school}"
            args.add(school)
        }

        if (commerce){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "closeToCommerce = :${commerce}"
            args.add(commerce)
        }

        if (park){
            query += if (conditions) " AND " else " WHERE "
            conditions = true
            query += "closeToSchool = :${park}"
            args.add(park)
        }

        query += if (conditions) " AND " else " WHERE "
        query += "creationDate >= :${dateLong}"
        args.add(dateLong)

        query += " AND count_photos >= ?"
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