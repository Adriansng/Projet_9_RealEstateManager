package com.openclassrooms.realestatemanager

import androidx.multidex.MultiDexApplication
import com.openclassrooms.realestatemanager.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidLogger(Level.NONE)
            androidContext(this@App)
            // modules
            modules(
                    appModule,
                    itemListModule,
                    itemDetailModule,
                    itemCreationModule,
                    itemSearchModule,
                    mapModule,
                    simulatorModule,
            )
        }
    }
}