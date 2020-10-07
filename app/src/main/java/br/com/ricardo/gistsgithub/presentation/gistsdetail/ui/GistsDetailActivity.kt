package br.com.ricardo.gistsgithub.presentation.gistsdetail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.ricardo.gistsgithub.R
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.databinding.ActivityGistsDetailBinding
import br.com.ricardo.gistsgithub.presentation.gistsdetail.viewmodel.GistsDetailViewModel
import kotlinx.android.synthetic.main.activity_gists_list.*

class GistsDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_GIST = "gist"

        fun getStartIntent(context: Context, gist: Gist): Intent {
            return Intent(context, GistsDetailActivity::class.java).apply {
                putExtra(EXTRA_GIST, gist)
            }
        }
    }

    private lateinit var gistObject: Gist
    private val viewModel: GistsDetailViewModel by viewModels()
    private val binding: ActivityGistsDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_gists_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gistObject = intent.getSerializableExtra(EXTRA_GIST) as Gist

        setSupportActionBar(binding.gistsDetailToolbar).apply {
            title = gistObject.owner.login
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGistInfos(gistObject)
        GistsDetailViewModel.loadImage(binding.gitDetailImageAvatar, gistObject.owner.avatar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}