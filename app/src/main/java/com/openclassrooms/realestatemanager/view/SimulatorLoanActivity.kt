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

    private lateinit var amountEditText: TextInputEditText
    private lateinit var contributionEditText: TextInputEditText
    private lateinit var interestEditText: TextInputEditText
    private lateinit var termEditText: TextInputEditText

    private lateinit var interestText: TextView
    private lateinit var mountText: TextView
    private lateinit var totalText: TextView

    private lateinit var device1: TextView
    private lateinit var device2: TextView

    // ------------------
    // TO CREATE
    // ------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)
        amountEditText = findViewById(R.id.loan_amount_edit_text)
        contributionEditText = findViewById(R.id.loan_contribution_edit_text)
        interestEditText = findViewById(R.id.loan_interest_edit_text)
        termEditText = findViewById(R.id.loan_term_edit_text)
        interestText = findViewById(R.id.loan_interest_txt)
        mountText = findViewById(R.id.loan_calcul_mount_txt)
        totalText = findViewById(R.id.loan_calcul_total_txt)

        device1 = findViewById(R.id.loan_device_txt)
        device2 = findViewById(R.id.loan_contribution_device_txt)
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
