package com.openclassrooms.realestatemanager

import android.app.Application
import com.openclassrooms.realestatemanager.di.module.appModule
import com.openclassrooms.realestatemanager.di.module.repoModule
import com.openclassrooms.realestatemanager.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Created by Adrian SENEGAS 16/03/2021.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            module { listOf(appModule, repoModule, viewModelModule) }
        }
    }
}