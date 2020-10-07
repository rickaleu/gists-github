package br.com.ricardo.gistsgithub.presentation.gistsfavourite.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.local.FavouriteDatabase
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.databinding.ActivityGistsFavouriteBinding
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.ui.adapter.FavouriteListAdapter
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.viewmodel.FavouriteViewModel
import br.com.ricardo.gistsgithub.utils.ItemGistsListener
import kotlinx.android.synthetic.main.activity_gists_favourite.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GistsFavouriteActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, GistsFavouriteActivity::class.java)
        }
    }

    private val viewModel: FavouriteViewModel by viewModel{
        val database = FavouriteDatabase.getDatabase(this)
        val favDao = database.favouriteDAO()
        parametersOf(favDao)
    }
    private lateinit var favouriteGistsListener : ItemGistsListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityGistsFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.gistsFavouriteToolbar).apply {
            title = getString(R.string.favourite_title_info)
        }
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.favouriteList.observe(this, Observer { fav ->
            if (!fav.isNullOrEmpty()) {
                with(gits_recycler_favourite) {
                    layoutManager = LinearLayoutManager(
                        this@GistsFavouriteActivity,
                        LinearLayoutManager.VERTICAL, false
                    )
                    setHasFixedSize(true)
                    adapter = FavouriteListAdapter(fav, favouriteGistsListener) { fav ->
                        Toast.makeText(this@GistsFavouriteActivity, fav.login, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        viewModel.starUnmarked.observe(this, Observer {
            it?.let { unmarked ->
                if (unmarked) {
                    Toast.makeText(this, R.string.favourites_toast_title_unmark, Toast.LENGTH_SHORT).show()
                }
            }
        })

        favouriteGistsListener = object : ItemGistsListener {
            override fun favouriteGist(gist: Gist) {}

            override fun getIdFav(id: Int) {
                viewModel.deleteFavouriteGist(id)
            }
        }


        viewModel.getFavouriteList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}