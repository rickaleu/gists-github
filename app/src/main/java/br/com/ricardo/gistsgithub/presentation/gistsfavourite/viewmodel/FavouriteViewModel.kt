package br.com.ricardo.gistsgithub.presentation.gistsfavourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.FavouriteListResult
import br.com.ricardo.gistsgithub.data.GistsListResult
import br.com.ricardo.gistsgithub.data.local.FavouriteDAO
import br.com.ricardo.gistsgithub.data.local.FavouriteEntity
import br.com.ricardo.gistsgithub.data.model.Favourite
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository.FavouriteRepository
import br.com.ricardo.gistsgithub.presentation.gistslist.viewmodel.GistsViewModel
import br.com.ricardo.gistsgithub.utils.GistsGitHubConstants
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: FavouriteRepository,
                         private val fav: FavouriteDAO) : ViewModel() {

    private val _starFavouritedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val starFavourted: LiveData<Boolean>
        get() = _starFavouritedMutableLiveData

    private val _favouriteListMutableLiveData: MutableLiveData<List<FavouriteEntity>> = MutableLiveData()
    val favouriteList: LiveData<List<FavouriteEntity>>
        get() = _favouriteListMutableLiveData

    fun favouriteGist(gist: Gist) {
        viewModelScope.launch {

            val favouriteParams = FavouriteParams(
                login = gist.owner.login,
                avatar = gist.owner.avatar
            )

            repository.createFavourite(favouriteParams, fav)

            _starFavouritedMutableLiveData.postValue(true)
        }
    }

    fun getFavouriteList() {
        viewModelScope.launch {

            repository.getFavouriteList(fav) { favResult ->
                when (favResult) {
                    is FavouriteListResult.Success -> {
                        _favouriteListMutableLiveData.postValue(favResult.favouriteList)
                    }
                }
            }
        }
    }

}