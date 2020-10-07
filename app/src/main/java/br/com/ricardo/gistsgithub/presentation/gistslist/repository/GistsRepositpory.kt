package br.com.ricardo.gistsgithub.presentation.gistslist.repository

import br.com.ricardo.gistsgithub.data.GistsListResult

interface GistsRepositpory {

    suspend fun fetchGistsList(page: Int, gistsResultCallBack: (result: GistsListResult) -> Unit)

}