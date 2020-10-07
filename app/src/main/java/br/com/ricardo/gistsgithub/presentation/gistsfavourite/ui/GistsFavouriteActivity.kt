package br.com.ricardo.gistsgithub.presentation.gistsfavourite.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.ricardo.gistsgithub.data.local.FavouriteDatabase
import br.com.ricardo.gistsgithub.databinding.ActivityGistsFavouriteBinding
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.viewmodel.FavouriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GistsFavouriteActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, GistsFavouriteActivity::class.java)
        }
    }

    //    private lateinit var gistObject: Gist
    private val viewModel: FavouriteViewModel by viewModel{
        val database = FavouriteDatabase.getDatabase(this)
        val favDao = database.favouriteDAO()
        parametersOf(favDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityGistsFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.gistsFavouriteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.gistsFavouriteToolbar.title = "FAVORITOS"

        viewModel.favouriteList.observe(this, Observer {
            if (it.isNullOrEmpty()) {

            }
        })


        viewModel.getFavouriteList()

//        binding.viewmodel = viewModel
//        binding.lifecycleOwner = this
    }
}