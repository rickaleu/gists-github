package br.com.ricardo.gistsgithub.di

import android.content.Context
import br.com.ricardo.gistsgithub.data.local.FavouriteDAO
import br.com.ricardo.gistsgithub.data.local.FavouriteDatabase
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository.FavouriteRepository
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository.FavouriteRepositoryImpl
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.viewmodel.FavouriteViewModel
import br.com.ricardo.gistsgithub.presentation.gistslist.repository.GistsRepositoryImpl
import br.com.ricardo.gistsgithub.presentation.gistslist.repository.GistsRepositpory
import br.com.ricardo.gistsgithub.presentation.gistslist.viewmodel.GistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gistsModule = module {
    factory<GistsRepositpory> {
        GistsRepositoryImpl()
    }

    viewModel {
        GistsViewModel(
            repository = get()
        )
    }
}

val favouriteModule = module {
    factory<FavouriteRepository> {
        FavouriteRepositoryImpl()
    }

    viewModel { (fav: FavouriteDAO) ->
        FavouriteViewModel(
            repository = get(),
            fav = fav
        )
    }
}