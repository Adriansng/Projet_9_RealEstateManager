package com.openclassrooms.realestatemanager.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.*
import android.widget.*
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
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewModel.ItemListViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.appcompat.widget.Toolbar as Toolbar1

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // --- FOR DATA ---


    private val viewModel : ItemListViewModel by viewModel()

    private var recyclerView: RecyclerView = findViewById(R.id.item_list)
    private var toolbar: Toolbar1 = findViewById(R.id.item_list_toolbar)
    private var drawerView: DrawerLayout = findViewById(R.id.drawer_layout)
    private var navView: NavigationView = findViewById(R.id.nav_view_menu)
    private var spinnerRealtors: Spinner = findViewById(R.id.nav_header_realtor_spinner)

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private var inEuro: Boolean = false

    private lateinit var realEstates: List<RealEstate>
    lateinit var realtor : Realtor
    private lateinit var realtors : List<Realtor>


    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        setSupportActionBar(toolbar)
        setUpRealEstates()
        setUpRealtors()
        setUpRealtor()
        setupToolbar()
        setupOpenNavDrawer()


        if (findViewById<FrameLayout>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }


    }

    // ------------------
    // REAL ESTATE
    // ------------------

    private fun setUpRealEstates(){
        viewModel.getRealEstates().observe(this, {
            realEstates = it
            setupRecyclerView(realEstates)
        })
    }
    // ------------------
    // REALTOR
    // ------------------

    private fun setUpRealtors(){
        viewModel.getRealtors().observe(this, {
             realtors= it
             setUpSpinnerRealtors()
        })
    }

    private fun setUpRealtor(){
        realtor = viewModel.getRealtor(realtors[0].id)
    }

    // ------------------
    // UI
    // ------------------

    // --- TOOLBAR ---

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_list_24)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_list_activity, menu)
       return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when( item.itemId){
            R.id.item_list_add_toolbar -> {
                //TODO (add RE)
                true
            }
            R.id.item_list_edit_toolbar -> {
                //TODO (edit RE)
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
                setUpSpinnerRealtors()
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
            R.id.menu_change_device_item -> popupChangeDevice()
            R.id.menu_loan_item -> launchSimulatorLoan()
        }
        drawerView.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUpSpinnerRealtors() {
        val adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, realtors)
        spinnerRealtors.adapter = adapter
    }

    // --- POPUP ---

    private fun popupChangeDevice(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change device")
        val listItems = arrayOf("Dollar", "Euro")
        builder.setSingleChoiceItems(listItems, -1){ dialogueInterface, i ->
            val device : String = listItems[i]
            dialogueInterface.dismiss()
            inEuro = device == "Euro"
            viewModel.updateDeviceForRealtor(realtor.id, inEuro)
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
            viewModel.addRealtor(textInputEditText.text.toString())
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

            override fun beforeTextChanged(p0: CharSequence?, p1: Int,
                                           p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int,
                                       p2: Int, p3: Int) {
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
    private fun getEditTextLayout(context: Context):ConstraintLayout{
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

    // --- LAUNCH SIMULATOR LOAN ---

    private fun launchSimulatorLoan(){
        val intent = Intent(this, SimulatorLoanActivity::class.java)
        intent.putExtra("Realtor", realtor.id)
        startActivity(intent)
    }


    // --- RECYCLER VIEW ---

    private fun setupRecyclerView(realEstates: List<RealEstate>) {
            recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, realEstates, twoPane, inEuro)
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                        private val values: List<RealEstate>,
                                        private val twoPane: Boolean,
                                        private val inEuro: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
            val item = v.tag as RealEstate
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ItemDetailFragment.ARG_ITEM_ID, item)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item)
                }
                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            // TODO (image index 1)
            holder.type.text = item.type
            holder.address.text = item.address
            if(inEuro){
                "${Utils.convertDollarToEuro(item.price.toInt())}+ €".also { holder.price.text = it }
            }else{
                "${item.price.toInt()}+ $".also { holder.price.text = it }
            }
            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.item_list_iv)
            val type: TextView = view.findViewById(R.id.item_list_type_txt)
            val address: TextView = view.findViewById(R.id.item_list_address_txt)
            val price: TextView = view.findViewById(R.id.item_list_price_txt)
        }
    }

}