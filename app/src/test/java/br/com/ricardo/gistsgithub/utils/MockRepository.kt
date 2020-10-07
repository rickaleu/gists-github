package br.com.ricardo.gistsgithub.utils

import br.com.ricardo.gistsgithub.data.GistsListResult
import br.com.ricardo.gistsgithub.presentation.gistslist.repository.GistsRepositpory

class MockRepository(private val result: GistsListResult) : GistsRepositpory {

    override suspend fun fetchGistsList(page: Int, gistsResultCallBack: (result: GistsListResult) -> Unit) {
        gistsResultCallBack(result)
    }

}