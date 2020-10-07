package br.com.ricardo.gistsgithub.presentation.gistslist.ui.adapter

import br.com.ricardo.gistsgithub.data.model.Gist

interface ItemListener {

    fun favouriteGist(gist: Gist)

}