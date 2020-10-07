package br.com.ricardo.gistsgithub

import android.app.Application
import br.com.ricardo.gistsgithub.di.favouriteModule
import br.com.ricardo.gistsgithub.di.gistsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)

            modules(listOf(gistsModule, favouriteModule))
        }
    }
}