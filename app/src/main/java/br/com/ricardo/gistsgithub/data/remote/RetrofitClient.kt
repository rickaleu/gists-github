package br.com.ricardo.gistsgithub.data.remote

import br.com.ricardo.gistsgithub.utils.GistsGitHubConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private lateinit var retrofit: Retrofit

    private fun initRetrofit() : Retrofit {
        val client = OkHttpClient.Builder()

        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(GistsGitHubConstants.BASE_URL)
            .client(client.build())
            .build()

        return retrofit
    }

    val SERVICE : GistsGitHubService = initRetrofit().create(GistsGitHubService::class.java)

}