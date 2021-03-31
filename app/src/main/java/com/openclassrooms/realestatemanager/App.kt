package com.openclassrooms.realestatemanager

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.openclassrooms.realestatemanager.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Adrian SENEGAS 16/03/2021.
 */
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidLogger(Level.NONE)
            androidContext(this@App)
            // modules
            modules  (appModule,
                    itemListModule,
                    itemDetailModule,
                    mapModule,
                    itemFilterModule,
                    simulatorModule,
                    itemCreationModule)
        }
    }
}