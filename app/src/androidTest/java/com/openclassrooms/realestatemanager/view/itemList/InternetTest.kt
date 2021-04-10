package com.openclassrooms.realestatemanager.view.itemList

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.TestCase

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowConnectivityManager
import org.robolectric.shadows.ShadowNetworkInfo

@RunWith(RobolectricTestRunner::class)
class InternetTest {

    private var shadowConnectivityManager: ShadowConnectivityManager? = null

    @Before
    fun setUp() {
        val connectivityManager = getConnectivityManager()
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager)
    }

    @Test
    fun connectivityTest_shouldWork() {
        var networkInfo: NetworkInfo = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true)
        shadowConnectivityManager?.setActiveNetworkInfo(networkInfo)
        TestCase.assertTrue(Utils.isInternetAvailable(RuntimeEnvironment.systemContext))
        networkInfo = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, false)
        shadowConnectivityManager?.setActiveNetworkInfo(networkInfo)
        TestCase.assertFalse(Utils.isInternetAvailable(RuntimeEnvironment.systemContext))
    }

    private fun getConnectivityManager(): ConnectivityManager {
        return RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
