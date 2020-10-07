package br.com.ricardo.gistsgithub.data

import br.com.ricardo.gistsgithub.data.local.FavouriteEntity
import br.com.ricardo.gistsgithub.data.model.Gist

sealed class GistsListResult {
    class Success(val gistsList: List<Gist>) : GistsListResult()
    class ApiError(val statusCode: Int) : GistsListResult()
}

sealed class FavouriteListResult {
    class Success(val favouriteList: List<FavouriteEntity>) : FavouriteListResult()
}