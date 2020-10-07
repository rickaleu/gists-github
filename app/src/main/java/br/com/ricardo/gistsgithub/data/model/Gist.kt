package br.com.ricardo.gistsgithub.data.model

import java.io.Serializable

data class Gist (
    val description: String?,
    val owner: OwnerInfo
) : Serializable