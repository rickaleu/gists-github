package br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository

import br.com.ricardo.gistsgithub.data.FavouriteListResult
import br.com.ricardo.gistsgithub.data.local.FavouriteDAO
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams

interface FavouriteRepository {

    suspend fun createFavourite(favouriteParams: FavouriteParams, fav: FavouriteDAO)

    suspend fun deleteFavourite(id: Int, fav: FavouriteDAO)

    suspend fun getFavouriteList(fav: FavouriteDAO, callback: (result: FavouriteListResult.Success) -> Unit)
}