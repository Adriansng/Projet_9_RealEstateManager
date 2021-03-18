package com.openclassrooms.realestatemanager.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.viewModel.SimulatorLoanViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SimulatorLoanActivity : AppCompatActivity() {

    // --- FOR DATA ---

    private val viewModel : SimulatorLoanViewModel by viewModel()

    private var amountEditText: TextInputEditText = findViewById(R.id.loan_amount_edit_text)
    private var contributionEditText: TextInputEditText = findViewById(R.id.loan_contribution_edit_text)
    private var interestEditText: TextInputEditText = findViewById(R.id.loan_interest_edit_text)
    private var termEditText: TextInputEditText = findViewById(R.id.loan_term_edit_text)

    private var interestText: TextView = findViewById(R.id.loan_interest_txt)
    private var mountText: TextView = findViewById(R.id.loan_calcul_mount_txt)
    private var totalText: TextView = findViewById(R.id.loan_calcul_total_txt)

    private var device1: TextView = findViewById(R.id.loan_device_txt)
    private var device2: TextView = findViewById(R.id.loan_contribution_device_txt)

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)
        val idRealtor = intent.getStringExtra("Realtor").toString().toLong()
        setupDevice(idRealtor)
        setupEditText()
    }

    // ------------------
    // DEVICE
    // ------------------

    private fun setupDevice(id: Long){
        if(viewModel.getRealtor(id).prefEuro){
            changeDevice("€")
        }else{
            changeDevice("$")
        }
    }

    private fun changeDevice(device: String){
        device1.text = device
        device2.text = device
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

    private fun editText(editText: TextInputEditText, text: String): Boolean {
        var check = false
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()){
                    termEditText.error = "$text is required."
                    check = false
                }else{
                    termEditText.error = ""
                    check = true
                    checkEditText()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        return check
    }
    private fun checkEditText(){
        if( editText(amountEditText, "Amount") &&
                editText(contributionEditText, "Contribution") &&
                editText(interestEditText, "Interest") &&
                editText(termEditText, "Term")) calculatorLoan()
    }

    // ------------------
    // CALCULATOR
    // ------------------
    private fun calculatorLoan(){
        viewModel.calculatorLoan(
                amountEditText.toString().toInt(),
                contributionEditText.toString().toInt(),
                interestEditText.toString().toDouble(),
                termEditText.toString().toInt())
    }

    // ------------------
    // RESULT
    // ------------------

    @SuppressLint("SetTextI18n")
    private fun displayResult(device: String) {
        viewModel.interest.observe(this,  {
            interestText.text = it.toString() + device
        })
        viewModel.mount.observe(this,  {
            mountText.text = it.toString() + device
        })
        viewModel.total.observe(this, {
            totalText.text = it.toString() + device
        })
    }
}
