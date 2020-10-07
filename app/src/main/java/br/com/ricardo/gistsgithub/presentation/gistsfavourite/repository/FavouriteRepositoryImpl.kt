package br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository

import br.com.ricardo.gistsgithub.data.FavouriteListResult
import br.com.ricardo.gistsgithub.data.local.FavouriteDAO
import br.com.ricardo.gistsgithub.data.local.toFavouriteEntity
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams

class FavouriteRepositoryImpl : FavouriteRepository {

    override suspend fun createFavourite(favouriteParams: FavouriteParams, fav: FavouriteDAO) {
        val favouriteEntity = favouriteParams.toFavouriteEntity()
        fav.save(favouriteEntity)
    }

    override suspend fun deleteFavourite(id: Int, fav: FavouriteDAO) {
        fav.delete(id)
    }


    override suspend fun getFavouriteList(
        fav: FavouriteDAO,
        callback: (result: FavouriteListResult.Success) -> Unit
    ) {
        val database = fav.getFavourite()
        callback.invoke(FavouriteListResult.Success(database))
    }


}