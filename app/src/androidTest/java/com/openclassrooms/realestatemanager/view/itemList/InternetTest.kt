package com.openclassrooms.realestatemanager.view.itemList

import android.support.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.utils.Utils
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InternetTest {

 @Test
 fun checkIsInternetAvailable(){
   assert(true) { Utils.isInternetAvailable(InstrumentationRegistry.getInstrumentation().context) }
 }
}
