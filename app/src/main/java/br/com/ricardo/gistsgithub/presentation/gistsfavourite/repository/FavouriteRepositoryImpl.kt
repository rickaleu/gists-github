package br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository

import br.com.ricardo.gistsgithub.data.FavouriteListResult
import br.com.ricardo.gistsgithub.data.GistsListResult
import br.com.ricardo.gistsgithub.data.local.FavouriteDAO
import br.com.ricardo.gistsgithub.data.local.FavouriteEntity
import br.com.ricardo.gistsgithub.data.local.toFavouriteEntity
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouriteRepositoryImpl : FavouriteRepository {

    override suspend fun createFavourite(favouriteParams: FavouriteParams, fav: FavouriteDAO) {
        val favouriteEntity = favouriteParams.toFavouriteEntity()
        fav.save(favouriteEntity)
    }

    override suspend fun getFavouriteList(fav: FavouriteDAO,
        callback: (result: FavouriteListResult.Success) -> Unit
    ) {
        val database = fav.getFavourite()

        callback.invoke(FavouriteListResult.Success(database))
    }


}