package com.example.newsapplication.di

import com.example.newsapplication.ui.favorites.FavoritesScreenViewModel
import com.example.newsapplication.data.local.AppDataBaseImpl
import com.example.newsapplication.data.local.datastore.FiltersDataStore
import com.example.newsapplication.data.local.repository.LocalRepository
import com.example.newsapplication.data.local.repository.LocalRepositoryImpl
import com.example.newsapplication.data.remote.PostService
import com.example.newsapplication.data.remote.PostServiceImpl
import com.example.newsapplication.data.remote.repository.RemoteRepository
import com.example.newsapplication.data.remote.repository.RemoteRepositoryImpl
import com.example.newsapplication.ui.detail.DetailScreenViewModel
import com.example.newsapplication.ui.search.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val applicationModule = module {
    /** Repositories */
    singleOf(::RemoteRepositoryImpl) { bind<RemoteRepository>() }
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    /** Services */
    singleOf(::PostServiceImpl) { bind<PostService>() }
    single { AppDataBaseImpl(get()) }
    single { FiltersDataStore(get()) }
    /** ViewModels */
    viewModelOf(::SearchScreenViewModel)
    viewModelOf(::FavoritesScreenViewModel)
    viewModelOf(::DetailScreenViewModel)
}
