package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.database.dao.RealtorDao
import com.openclassrooms.realestatemanager.repositories.RealtorRepository
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.SimulatorLoanActivity
import com.openclassrooms.realestatemanager.viewModel.SimulatorLoanViewModel
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Throws

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class UnitTest {

    private val nowDate = SimpleDateFormat("dd/MM/yyyy").format(Date())

    @Test
    fun convertDollarToEuro() {
        assertEquals(121800, Utils.convertDollarToEuro(150000))
    }

    @Test
    fun convertEuroToDollar() {
        assertEquals(150000, Utils.convertEuroToDollar(121800))
    }

    @Test
    fun getTodayDate() {
        assertEquals(nowDate, Utils.getTodayDate())
    }
}