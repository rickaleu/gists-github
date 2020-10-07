package br.com.ricardo.gistsgithub.data.model

import java.io.Serializable

data class OwnerInfo (
    val login: String,
    val avatar: String,
) : Serializable