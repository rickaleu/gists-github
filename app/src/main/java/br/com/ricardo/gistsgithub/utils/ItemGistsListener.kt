package br.com.ricardo.gistsgithub.utils

import br.com.ricardo.gistsgithub.data.model.Gist

interface ItemGistsListener {

    fun favouriteGist(gist: Gist)

    fun getIdFav(id: Int)

}