package com.openclassrooms.realestatemanager.view.itemCreation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.utils.Converters
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.itemDetail.ItemDetailFragment
import com.openclassrooms.realestatemanager.view.itemList.ItemListActivity
import com.openclassrooms.realestatemanager.viewModel.ItemCreationViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.EasyPermissions
import java.util.*


class ItemCreationRealEstateActivity : AppCompatActivity() {


    private var isEdit: Boolean = false
    private val viewModel : ItemCreationViewModel by viewModel()

    private val perms = Manifest.permission.READ_EXTERNAL_STORAGE
    private val rcImagePerms = 100
    private val rcChoosePhoto = -1

    private lateinit var currentRealtor: Realtor

    private var listPhoto = mutableListOf<Photo>()

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
    private lateinit var recyclerView: RecyclerView

     private lateinit var closeToSchool: CheckBox
     private lateinit var closeToCommerce: CheckBox
     private lateinit var closeToPark: CheckBox


    private lateinit var button: ImageView
    private lateinit var buttonAddPhoto: ImageView

    private var realEstate : RealEstate = RealEstate.default()
    private var photoCreate : Photo = Photo.default()

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_item_creation)
        title = this.getString(R.string.add_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCurrentRealtor()
    }

    // ------------------
    // UI
    // ------------------

    private fun setUpUi(){
        soldSwitchMaterial = findViewById(R.id.add_RE_sold_switch)
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
        recyclerView = findViewById(R.id.add_RE_photo_recyclerview)
        buttonAddPhoto = findViewById(R.id.add_RE_button_image_iv)
        setUpButtonAddImage()
        button = findViewById(R.id.add_RE_button_add_iv)
        button.setOnClickListener { checkCalculator() }

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
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()) {
                    editText.error = "$text is required."
                } else {
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
                "Penthouse",
        )
        val adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, items)
        typeAutoCompleteTextView.setAdapter(adapter)
    }

    // --- DEVICE ---

    private fun setUpDevice(){
        if(currentRealtor.prefEuro){
            val priceLayout = findViewById<TextInputLayout>(R.id.add_RE_price_text)
            priceLayout.startIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_euro_24)
        }
    }


    // ------------------
    // SET UP EDIT
    // ------------------

    private fun editRealEstate(realEstate: RealEstate){
        title = getString(R.string.add_title_edit)
        setUpPhotoRealEstate(realEstate.id)
        setUpDropDownMenu()
        typeAutoCompleteTextView.setText(realEstate.type)
        if(currentRealtor.prefEuro){
            priceEdit.setText(Utils.convertDollarToEuro(realEstate.price).toString(), TextView.BufferType.EDITABLE)
        }else{
            priceEdit.setText(realEstate.price.toString(), TextView.BufferType.EDITABLE)
        }
        surfaceEdit.setText(realEstate.area.toString(), TextView.BufferType.EDITABLE)
        roomEdit.setText(realEstate.numberRoom.toString(), TextView.BufferType.EDITABLE)
        bedroomEdit.setText(realEstate.numberBedroom.toString(), TextView.BufferType.EDITABLE)
        bathroomEdit.setText(realEstate.numberBathroom.toString(), TextView.BufferType.EDITABLE)
        addressEdit.setText(realEstate.address, TextView.BufferType.EDITABLE)
        cityEdit.setText(realEstate.city, TextView.BufferType.EDITABLE)
        zipEdit.setText(realEstate.zipCode.toString(), TextView.BufferType.EDITABLE)
        descriptionEdit.setText(realEstate.descriptionRealEstate, TextView.BufferType.EDITABLE)
        closeToSchool.isChecked = realEstate.closeToSchool
        closeToCommerce.isChecked = realEstate.closeToCommerce
        closeToPark.isChecked = realEstate.closeToPark
        if(realEstate.saleCreation!= null) soldSwitchMaterial.isChecked = true
    }

    private fun setUpPhotoRealEstate(idRealEstate: Long){
        viewModel.getListPhoto(idRealEstate).observe(this) {
            for (element in it ){
                listPhoto.add(element)
            }
            setUpRecyclerView(listPhoto)
        }
    }

    // ------------------
    // PHOTO
    // ------------------

    private fun setUpButtonAddImage(){
        buttonAddPhoto.setOnClickListener {
            chooseImageFromPhone()
        }
    }

    private fun setUpRecyclerView(list: List<Photo>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ItemListCreationRecyclerViewAdapter(list,
                onDeletePhoto = {
                    removePhoto(it)
                },
                onRenamePhoto = {
                    popupDescription(it.uri)
                }
        )
    }

    private fun removePhoto(photo: Photo) {
        listPhoto.remove(photo)
        setUpRecyclerView(listPhoto)
    }

    private fun popupDescription(photoAdd: String){
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Photo description") // dialog message view
        val constraintLayout = getEditTextLayout(this)
        builder.setView(constraintLayout)

        val textInputLayout = constraintLayout.
        findViewWithTag<TextInputLayout>("textInputLayoutTag")
        val textInputEditText = constraintLayout.
        findViewWithTag<TextInputEditText>("textInputEditTextTag")

        // alert dialog positive button
        builder.setPositiveButton("Submit"){ dialog, _ ->
            val iterator = listPhoto.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (item.uri == photoAdd) {
                    iterator.remove()
                }
            }
            listPhoto.add(Photo(
                    idPhoto = (listPhoto.lastIndex+1).toLong(),
                    descriptionPhoto = textInputEditText.text.toString(),
                    uri = photoAdd,
                    idRealEstate = 0
            ))
            setUpRecyclerView(listPhoto)
            dialog.dismiss()
        }

        // alert dialog other buttons
        builder.setNegativeButton("No", null)
        builder.setNeutralButton("Cancel", null)

        // set dialog non cancelable
        builder.setCancelable(false)

        // finally, create the alert dialog and show it
        val dialog = builder.create()

        dialog.show()

        // initially disable the positive button
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).isEnabled = false

        // edit text text change listener
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(
                    p0: CharSequence?, p1: Int,
                    p2: Int, p3: Int,
            ) {
            }

            override fun onTextChanged(
                    p0: CharSequence?, p1: Int,
                    p2: Int, p3: Int,
            ) {
                if (p0.isNullOrBlank()) {
                    textInputLayout.error = "Name is required."
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                            .isEnabled = false
                } else {
                    textInputLayout.error = ""
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                            .isEnabled = true
                }
            }
        })
    }

    // get edit text layout
    private fun getEditTextLayout(context: Context): ConstraintLayout {
        val constraintLayout = ConstraintLayout(context)
        val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        constraintLayout.layoutParams = layoutParams
        constraintLayout.id = View.generateViewId()

        val textInputLayout = TextInputLayout(context)
        textInputLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        layoutParams.setMargins(
                32.toDp(context),
                8.toDp(context),
                32.toDp(context),
                8.toDp(context)
        )
        textInputLayout.layoutParams = layoutParams
        textInputLayout.hint = "Input name"
        textInputLayout.id = View.generateViewId()
        textInputLayout.tag = "textInputLayoutTag"


        val textInputEditText = TextInputEditText(context)
        textInputEditText.id = View.generateViewId()
        textInputEditText.tag = "textInputEditTextTag"

        textInputLayout.addView(textInputEditText)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintLayout.addView(textInputLayout)
        return constraintLayout
    }

    // extension method to convert pixels to dp
    private fun Int.toDp(context: Context):Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()


    private var launcher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == rcChoosePhoto) {
            if (result.resultCode == RESULT_OK) { //SUCCESS
                val uriImageSelected: Uri? = result.data?.data
                uriImageSelected?.let { contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)}
                popupDescription(uriImageSelected.toString())
            } else {
                Toast.makeText(this, R.string.toast_choose_image, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_perms), rcImagePerms, perms)
            return
        }
        val i = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(i)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    // ------------------
    // LOCATION
    // ------------------

    private fun getLocation(realEstate: RealEstate): LatLng? {
        var latLng: LatLng?
        val geoCoder = Geocoder(this, Locale.getDefault())
        var addresses: MutableList<Address> = geoCoder.getFromLocationName(realEstate.address+" "
                +realEstate.city+" "
                +realEstate.zipCode,
                1)
        val location : Address = addresses[0]
        latLng  = LatLng(location.latitude, location.longitude)
        return latLng
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
        }else{
            realEstate.saleCreation = null
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
        realEstate.photo = this.listPhoto[0].uri
        if(Utils.isInternetAvailable(this)) {
            realEstate.location = getLocation(realEstate)
        }
        viewModel.addRealEstate(realEstate)
        val idRealEstate : Long = if(!isEdit){
            viewModel.getRealEstateLast("Real_Estate")!!
        }else{
            realEstate.id
        }
        viewModel.deleteAllPhoto(idRealEstate)
        for(element in listPhoto){
            element.idRealEstate = idRealEstate
            viewModel.insertPhoto(element)
        }
        finishActivity()
    }


    // ------------------
    // REAL ESTATE
    // ------------------

    private fun getRealEstate(id: Long): RealEstate = viewModel.getRealEstate(id)

    private fun setUpEditRealEstate(){
        val realEstateId = intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID)
        if (realEstateId != null) {
            realEstate = getRealEstate(realEstateId.toString().toLong())
            editRealEstate(realEstate)
            isEdit = true
        }
    }

    // ------------------
    // REALTOR
    // ------------------
    private fun getCurrentRealtor(){
        viewModel.getRealtorCurrent().observe(this, {
            currentRealtor = it
            setUpUi()
            setUpEditRealEstate()
        })
    }

    // ------------------
    // FINISH ACTIVITY
    // ------------------

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finishActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun finishActivity(){
        intent = Intent(this, ItemListActivity::class.java)
        startActivity(intent)
    }
}
