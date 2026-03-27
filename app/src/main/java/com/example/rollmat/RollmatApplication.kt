package com.example.rollmat

import android.app.Application
import com.example.rollmat.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RollmatApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Pokrenuti Koin -> Dependency Injection framework.
         */
        startKoin {
            androidLogger()
            androidContext(this@RollmatApplication)
            modules(appModule)
        }
    }
}
