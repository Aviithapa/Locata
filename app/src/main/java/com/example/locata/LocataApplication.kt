package com.example.locata

import android.app.Application
import com.example.locata.data.api.ApiService
import com.example.locata.data.api.NetworkConnectionInterceptor
import com.example.locata.data.db.AppDatabase
import com.example.locata.data.repository.PreferenceProvider
import com.example.locata.data.repository.UserRepository
import com.example.locata.ui.main.view.main.ui.home.HomeViewModelFactory
import com.example.locata.ui.main.viewModel.authentication.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class LocataApplication : Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule((this@LocataApplication)))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
         bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
     }
}