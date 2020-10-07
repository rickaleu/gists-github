package br.com.ricardo.gistsgithub.presentation.gistslist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.local.FavouriteDatabase
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.presentation.gistsdetail.ui.GistsDetailActivity
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.ui.GistsFavouriteActivity
import br.com.ricardo.gistsgithub.presentation.gistsfavourite.viewmodel.FavouriteViewModel
import br.com.ricardo.gistsgithub.presentation.gistslist.ui.adapter.GistsListAdapter
import br.com.ricardo.gistsgithub.utils.ItemGistsListener
import br.com.ricardo.gistsgithub.presentation.gistslist.viewmodel.GistsViewModel
import br.com.ricardo.gistsgithub.utils.ConnectionUtils
import br.com.ricardo.gistsgithub.utils.GistsGitHubConstants
import kotlinx.android.synthetic.main.activity_gists_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GistsListActivity : AppCompatActivity() {

    private val viewModel: GistsViewModel by viewModel()
    private val favouriteViewModel: FavouriteViewModel by viewModel{
        val database = FavouriteDatabase.getDatabase(this)
        val favDao = database.favouriteDAO()
        parametersOf(favDao)
    }

    private val connectionUtils = ConnectionUtils(this)
    private lateinit var favouriteGistsListener : ItemGistsListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gists_list)

        gists_toolbar.title = getString(R.string.app_name)
        setSupportActionBar(gists_toolbar)

        if (!connectionUtils.isConnectionAvailable(this)) {
            gists_view_flipper.visibility = View.INVISIBLE
            Toast.makeText(this, R.string.gists_no_network, Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getGistsList(GistsGitHubConstants.GET_FIELD_3)
        }

        viewModel.gistsList.observe(this, Observer {
            it?.let { gist ->
                with(gists_recycler) {
                    layoutManager = LinearLayoutManager(
                        this@GistsListActivity,
                        LinearLayoutManager.VERTICAL, false
                    )
                    setHasFixedSize(true)
                    adapter = GistsListAdapter(gist, favouriteGistsListener) { gist ->
                        this@GistsListActivity.startActivity(
//                            GistsDetailActivity.getStartGistIntent(this@GistsListActivity, gist)
                        GistsDetailActivity.getStartIntent(this@GistsListActivity, gist)
                        )
                    }
                }
            }
        })

        viewModel.viewFlipper.observe(this, Observer {
            it?.let { viewFlipper ->
                gists_view_flipper.displayedChild = viewFlipper.first

                viewFlipper.second?.let { errorMessage ->
                    gists_text_error.text = getString(errorMessage)
                }
            }
        })

        favouriteViewModel.starMarked.observe(this, Observer {
            it?.let { marked ->
                if (marked) {
                    Toast.makeText(this, "Favoritado", Toast.LENGTH_SHORT).show()
                }
            }
        })


        favouriteGistsListener = object : ItemGistsListener {
            override fun favouriteGist(gist: Gist) {
                favouriteViewModel.favouriteGist(gist)
            }

            override fun getIdFav(id: Int) {}
        }

        gists_icon_favourite.setOnClickListener {
            startActivity(GistsFavouriteActivity.getStartIntent(this))
        }

//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        gists_recycler.addOnScrollListener(
//            InfiniteScrollListener({
//                viewModel.getGistsList()
//            }, layoutManager)
//        )
    }
}