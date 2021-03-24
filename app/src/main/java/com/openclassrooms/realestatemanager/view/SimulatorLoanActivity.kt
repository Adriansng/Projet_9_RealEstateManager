package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.viewModel.SimulatorLoanViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SimulatorLoanActivity : AppCompatActivity() {

    // --- FOR DATA ---
    private val viewModel : SimulatorLoanViewModel by viewModel()

    private lateinit var amountLayoutText: TextInputLayout
    private lateinit var contributionLayoutText: TextInputLayout

    private lateinit var amountEditText: TextInputEditText
    private lateinit var contributionEditText: TextInputEditText
    private lateinit var interestEditText: TextInputEditText
    private lateinit var termEditText: TextInputEditText

    private lateinit var interestText: TextView
    private lateinit var mountText: TextView
    private lateinit var totalText: TextView

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)
        title = applicationContext.getString(R.string.loan_title)

        amountLayoutText = findViewById(R.id.loan_amount_txt)
        contributionLayoutText = findViewById(R.id.loan_contribution_txt)

        amountEditText = findViewById(R.id.loan_amount_edit_text)
        contributionEditText = findViewById(R.id.loan_contribution_edit_text)
        interestEditText = findViewById(R.id.loan_interest_edit_text)
        termEditText = findViewById(R.id.loan_term_edit_text)
        interestText = findViewById(R.id.loan_calcul_interest_txt)
        mountText = findViewById(R.id.loan_calcul_mount_txt)
        totalText = findViewById(R.id.loan_calcul_total_txt)
        getCurrentRealtor()
        setupEditText()
        displayResult(" $")
    }

    // ------------------
    // DEVICE
    // ------------------

    private fun setupDevice(prefEuro: Boolean){
        if(prefEuro){
            changeDevice(" €")
        }else{
            changeDevice(" $")
        }
    }

    private fun changeDevice(device: String){
        amountLayoutText.suffixText = device
        contributionLayoutText.suffixText = device
        displayResult(device)
    }

    // ------------------
    // EDIT TEXT
    // ------------------

    private fun setupEditText(){
        editText(amountEditText, "Amount")
        editText(contributionEditText, "Contribution")
        editText(interestEditText, "Interest")
        editText(termEditText, "Term")
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
                    checkCalculator()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun checkCalculator(){
        if(amountEditText.text.toString() != "" &&
                contributionEditText.text.toString() != ("")&&
                interestEditText.text.toString() != ("") &&
                termEditText.text.toString() != ("")){
            calculatorLoan()
        }
    }

    // ------------------
    // CALCULATOR
    // ------------------
    private fun calculatorLoan(){
        viewModel.calculatorLoan(
                amountEditText.text.toString().toInt(),
                contributionEditText.text.toString().toInt(),
                interestEditText.text.toString().toDouble(),
                termEditText.text.toString().toInt())
    }

    // ------------------
    // RESULT
    // ------------------

    private fun displayResult(device :String) {
        viewModel.interest.observe(this,  { it ->
            (it.toString() + device).also { interestText.text = it }
        })
        viewModel.mount.observe(this,  { it ->
            (it.toString() + device).also { mountText.text = it }
        })
        viewModel.total.observe(this, { it ->
            (it.toString() + device).also { totalText.text = it }
        })
    }

    // ------------------
    // REALTOR
    // ------------------

    private fun getCurrentRealtor(){
        viewModel.getRealtorCurrent().observe(this, {
            val currentRealtor = it
            setupDevice(currentRealtor.prefEuro)
        })
    }

}
