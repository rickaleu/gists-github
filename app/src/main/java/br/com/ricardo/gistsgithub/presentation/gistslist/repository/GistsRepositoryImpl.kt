package br.com.ricardo.gistsgithub.presentation.gistslist.repository

import br.com.ricardo.gistsgithub.data.GistsListResult
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GistsRepositoryImpl: GistsRepositpory {

    private val service = RetrofitClient.SERVICE

    override suspend fun fetchGistsList(page: Int, gistsResultCallBack: (result: GistsListResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            val resultGistsList = mutableListOf<Gist>()

            val response = service.getGists(page)
            withContext(Dispatchers.IO) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { response ->
                            for (gist in response) {
                                val result = gist.getGist()
                                resultGistsList.add(result)
                            }
                            gistsResultCallBack.invoke(GistsListResult.Success(resultGistsList))
                        }
                    } else {
                        gistsResultCallBack.invoke(GistsListResult.ApiError(response.code()))
                    }

                } catch (e: Exception) {
                    gistsResultCallBack.invoke(GistsListResult.ApiError(response.code()))
                }
            }
        }
    }
}