package br.com.ricardo.gistsgithub.data.remote

import br.com.ricardo.gistsgithub.data.response.GistsBodyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GistsGitHubService {

    @GET("/gists/public")
    suspend fun getGists(@Query("page") page: Int) : Response<List<GistsBodyResponse>>
}