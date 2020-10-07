package br.com.ricardo.gistsgithub.presentation.gistslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.GistsListResult
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.presentation.gistslist.repository.GistsRepositpory
import br.com.ricardo.gistsgithub.utils.GistsGitHubConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GistsViewModel(private val repository: GistsRepositpory) : ViewModel() {

    companion object {
        private const val VIEW_FLIPPER_REPOS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    private val _gistsListMutableLiveData: MutableLiveData<List<Gist>> = MutableLiveData()
    val gistsList: LiveData<List<Gist>>
        get() = _gistsListMutableLiveData

    private val _viewFlipperMutableLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val viewFlipper: LiveData<Pair<Int, Int?>>
        get() = _viewFlipperMutableLiveData


    fun getGistsList(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                repository.fetchGistsList(page) { gistsResult: GistsListResult ->
                    when (gistsResult) {
                        is GistsListResult.Success -> {
                            _gistsListMutableLiveData.postValue(gistsResult.gistsList)
                            _viewFlipperMutableLiveData.postValue(Pair(VIEW_FLIPPER_REPOS, null))
                        }
                        is GistsListResult.ApiError -> {
                            if (gistsResult.statusCode == GistsGitHubConstants.HTTP_ERROR_UNPROCESSABLE_ENTITY) {
                                _viewFlipperMutableLiveData.postValue(
                                    Pair(VIEW_FLIPPER_ERROR, R.string.gists_view_flipper_error_422))
                            } else {
                                _viewFlipperMutableLiveData.postValue(
                                    Pair(VIEW_FLIPPER_ERROR, R.string.gists_view_flipper_error_500))
                            }
                        }
                    }
                }
            }
        }
    }

}