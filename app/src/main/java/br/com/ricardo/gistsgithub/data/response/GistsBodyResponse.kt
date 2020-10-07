package br.com.ricardo.gistsgithub.data.response

import br.com.ricardo.gistsgithub.data.model.Gist
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GistsBodyResponse (
    @Json(name = "description")
    val description: String?,
    @Json(name = "owner")
    val owner: GistsOwnerResponse
) {
    fun getGist() = Gist (
        description = this.description,
        owner = this.owner.getGistOwner()
    )
}