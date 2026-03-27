package com.example.rollmat.di

import com.example.rollmat.data.AppDatabase
import com.example.rollmat.login.LoginViewModel
import com.example.rollmat.main.MainViewModel
import com.example.rollmat.profile.repository.ProfileRepository
import com.example.rollmat.profile.repository.ProfileRepositoryImpl
import com.example.rollmat.profile.ui.ProfileViewModel
import com.example.rollmat.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    /**
     * Singleton pattern -> neki objekt ili klasa imaju jednu jedinu instancu.
     */
    single { AppDatabase.getDatabase(androidContext()) }

    // DAO
    single { get<AppDatabase>().sessionDao() }
    single { get<AppDatabase>().userDao() }

    // Repository
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }

    // ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}
