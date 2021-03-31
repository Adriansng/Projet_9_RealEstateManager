package com.openclassrooms.realestatemanager.view.itemList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.view.ItemMapActivity
import com.openclassrooms.realestatemanager.view.SimulatorLoanActivity
import com.openclassrooms.realestatemanager.view.itemCreation.ItemCreationRealEstateActivity
import com.openclassrooms.realestatemanager.viewModel.ItemListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.appcompat.widget.Toolbar as Toolbar1


class ItemListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // --- FOR DATA ---

    private val viewModel : ItemListViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar1
    private lateinit var drawerView: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var spinnerRealtors: Spinner

    private var twoPane: Boolean = false
    private var inEuro: Boolean = false

    private lateinit var realEstates: List<RealEstate>
    private lateinit var photos: List<Photo>
    lateinit var realtor : Realtor
    private var idRealtor : Long = 1
    private lateinit var realtors : List<Realtor>


    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        setUpUI()
        setUpRealtorCurrent(idRealtor)
        initRealtorCurrent()
        setUpRealtors()
    }

    // ------------------
    // REAL ESTATE
    // ------------------

    private fun setUpRealEstates(inEuro: Boolean){
        viewModel.getRealEstates().observe(this, {
            realEstates = it
            setupRecyclerView(realEstates, inEuro)
        })
    }
    // ------------------
    // REALTOR
    // ------------------

    private fun initRealtorCurrent(){
        viewModel.getRealtorCurrent().observe(this){
            realtor = it
            idRealtor = realtor.id
            setUpRealEstates(realtor.prefEuro)
        }
    }
    private fun setUpRealtors(){
        viewModel.getRealtors().observe(this, {
            realtors = it
        })
    }

    private fun setUpRealtor(id: Long){
        realtor = viewModel.getRealtor(id)
        setUpRealEstates(realtor.prefEuro)
    }

    private fun setUpRealtorCurrent(id: Long){
        realtor = viewModel.getRealtor(id)
        viewModel.setRealtorCurrent(realtor)
    }

    private fun updateRealtor(realtor: Realtor){
        viewModel.addRealtor(realtor)
    }

    // ------------------
    // UI
    // ------------------

    // --- SETUP ---

    private fun setUpUI(){
        if (findViewById<FrameLayout>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        recyclerView = findViewById(R.id.item_list)
        toolbar = findViewById(R.id.item_list_toolbar)
        drawerView = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view_menu)
        setSupportActionBar(toolbar)
        setupToolbar()
        setupOpenNavDrawer()
    }

    // --- TOOLBAR ---

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_list_24)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_list_activity, menu)
        menu?.findItem(R.id.item_list_edit_toolbar)?.isVisible = !twoPane
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when( item.itemId){
            R.id.item_list_add_toolbar -> {
                launchItemCreation()
                true
            }
            R.id.item_list_edit_toolbar -> {
                launchItemCreation()
                true
            }
            R.id.item_list_filter_toolbar -> {
                //TODO (filter list)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    // --- NAVIGATION DRAWER ---

    private fun setupOpenNavDrawer() {
        val toggle = ActionBarDrawerToggle(this,
                drawerView,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        drawerView.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        drawerView.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                setUpSpinnerRealtors()
            }

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun onBackPressed() {
        if(drawerView.isDrawerOpen(GravityCompat.START)){
            drawerView.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_add_realtor_item -> popupAddRealtor()
            R.id.menu_change_device_item -> popupChangeDevice(realtor)
            R.id.menu_loan_item -> launchSimulatorLoan()
            R.id.menu_map_item -> launchMap()
        }
        drawerView.closeDrawer(GravityCompat.START)
        return true
    }

    // --- SPINNER REALTORS ---

    private fun setUpSpinnerRealtors() {
        // --- FOR DATA ---
        val names  = mutableListOf<String>()
        realtors.forEachIndexed{ _, realtor ->
            names.add(realtor.name)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRealtors = findViewById(R.id.nav_header_realtor_spinner)
        spinnerRealtors.adapter = adapter
        spinnerRealtors.setSelection(idRealtor.toInt() - 1)
        spinnerRealtors.onItemSelectedListener = object :AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idRealtor = id +1
                setUpRealtorCurrent(idRealtor)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    // --- POPUP ---

    private fun popupChangeDevice(realtor: Realtor){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change device")
        val listItems = arrayOf("Dollar", "Euro")
        builder.setSingleChoiceItems(listItems, -1){ dialogueInterface, i ->
            val device : String = listItems[i]
            inEuro = device == "Euro"
            realtor.prefEuro = inEuro
            updateRealtor(realtor)
            setUpRealtor(realtor.id)
            dialogueInterface.dismiss()
        }
        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel") { dialog, _ ->
            // Do something when click the neutral button
            dialog.cancel()
        }
        val mDialog = builder.create()
        mDialog.show()
    }


    private fun popupAddRealtor(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add realtor") // dialog message view
        val constraintLayout = getEditTextLayout(this)
        builder.setView(constraintLayout)

        val textInputLayout = constraintLayout.
        findViewWithTag<TextInputLayout>("textInputLayoutTag")
        val textInputEditText = constraintLayout.
        findViewWithTag<TextInputEditText>("textInputEditTextTag")

        // alert dialog positive button
        builder.setPositiveButton("Submit"){ dialog, _ ->
            val realtorAdd : Realtor= Realtor.default()
            realtorAdd.name = textInputEditText.text.toString()
            updateRealtor(realtorAdd)
            setUpSpinnerRealtors()
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
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

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
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .isEnabled = false
                } else {
                    textInputLayout.error = ""
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
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

    // ------------------
    // LAUNCHER
    // ------------------

    // --- LAUNCH SIMULATOR LOAN ---

    private fun launchSimulatorLoan(){
        val intent = Intent(this, SimulatorLoanActivity::class.java)
        intent.putExtra("Realtor", realtor.prefEuro)
        startActivity(intent)
    }

    // --- LAUNCH ITEM CREATION  ---

    private fun launchItemCreation(){
        val intent = Intent(this, ItemCreationRealEstateActivity::class.java)
        startActivity(intent)
    }


    // --- LAUNCH MAP  ---

    private fun launchMap() {
        val intent = Intent(this, ItemMapActivity::class.java)
        startActivity(intent)
    }



    // --- RECYCLER VIEW REAL ESTATE---

    private fun setupRecyclerView(realEstates: List<RealEstate>, inEuro: Boolean) {
            recyclerView.adapter = ItemListRecyclerViewAdapter(this, realEstates, twoPane, inEuro)
    }
}