package br.com.ricardo.gistsgithub.data.response

import br.com.ricardo.gistsgithub.data.model.OwnerInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GistsOwnerResponse (
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar_url")
    val avatar: String
) {
    fun getGistOwner() = OwnerInfo(
        login = this.login,
        avatar = this.avatar
    )
}