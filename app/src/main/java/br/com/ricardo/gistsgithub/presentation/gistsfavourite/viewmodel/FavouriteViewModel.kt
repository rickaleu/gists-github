package br.com.ricardo.gistsgithub.presentation.gistsfavourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricardo.gistsgithub.data.FavouriteListResult
import br.com.ricardo.gistsgithub.data.local.FavouriteDAO
import br.com.ricardo.gistsgithub.data.local.FavouriteEntity
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.FavouriteParams
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.repository.FavouriteRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: FavouriteRepository,
                         private val fav: FavouriteDAO) : ViewModel() {

    private val _starMerkedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val starMarked: LiveData<Boolean>
        get() = _starMerkedMutableLiveData

    private val _starUnmarkedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val starUnmarked: LiveData<Boolean>
        get() = _starUnmarkedMutableLiveData

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
            _starMerkedMutableLiveData.postValue(true)
        }
    }

    fun deleteFavouriteGist(id: Int) {
        viewModelScope.launch {

            repository.deleteFavourite(id, fav)
            _starUnmarkedMutableLiveData.postValue(true)
            getFavouriteList()
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